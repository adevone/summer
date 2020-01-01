package summer.execution

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import summer.SummerLogger
import summer.SummerPresenter
import summer.execution.mix.MixSource
import summer.execution.mix.MixSourceExecutor
import summer.execution.reducer.ReducerExecutor
import summer.execution.reducer.SummerReducer
import summer.execution.source.SourceExecutor
import summer.execution.source.SummerSource
import kotlin.coroutines.CoroutineContext

/**
 * Manages executions of sources ([SummerSource], [SummerReducer], [MixSource])
 */
interface SummerExecutor : CoroutineScope {

    val defaultWorkContext: CoroutineContext

    /**
     * Called on each unhandled error in any executor
     */
    fun onFailure(e: Throwable)

    /**
     * Factory method for [SourceExecutor]
     */
    fun <TEntity, TParams> SummerSource<TEntity, TParams>.executor(
        interceptor: SummerExecutorInterceptor<TEntity, TParams> = NoInterceptor(),
        workContext: CoroutineContext = defaultWorkContext,
        onExecute: suspend (_: TParams) -> Unit = { _ -> },
        onFailure: suspend (Throwable, _: TParams) -> Unit = { e, _ -> throw e },
        onCancel: suspend (TParams) -> Unit = { _ -> },
        onSuccess: suspend (TEntity, _: TParams) -> Unit = { _, _ -> }
    ): SourceExecutor<TEntity, TParams>

    /**
     * Factory method for [ReducerExecutor]
     */
    fun <TEntity, TParams> SummerReducer<TEntity, TParams>.executor(
        interceptor: SummerExecutorInterceptor<TEntity, TParams?> = NoInterceptor(),
        workContext: CoroutineContext = defaultWorkContext,
        onExecute: suspend (_: TParams?) -> Unit = { _ -> },
        onFailure: suspend (Throwable, _: TParams?) -> Unit = { e, _ -> throw e },
        onCancel: suspend (TParams?) -> Unit = { _ -> },
        onSuccess: suspend (TEntity, _: TParams?) -> Unit = { _, _ -> }
    ): ReducerExecutor<TEntity, TParams>

    /**
     * Factory method for [MixSourceExecutor]
     */
    fun <T, TSourceEntity, TMixEntity, TSourceParams> MixSource<T, TSourceEntity, TMixEntity, TSourceParams>.executor(
        interceptor: SummerExecutorInterceptor<T, TSourceParams> = NoInterceptor(),
        onExecute: suspend (_: TSourceParams) -> Unit = { _ -> },
        onFailure: suspend (Throwable, _: TSourceParams) -> Unit = { e, _ -> throw e },
        onCancel: suspend (TSourceParams) -> Unit = { _ -> },
        onSuccess: suspend (T, TSourceParams) -> Unit = { _, _ -> }
    ): MixSourceExecutor<T, TSourceParams>

    /**
     * @return [MixSource] that mixes [this] with [mix].
     * Triggers every times when executed or
     * [mix] produces new value (but only after being called at least once)
     */
    fun <TSourceEntity, TSourceParams, TMixEntity, TMixParams, T> SummerSource<TSourceEntity, TSourceParams>.mix(
        mix: SummerReducer<TMixEntity, TMixParams>,
        transform: (TSourceEntity, TMixEntity) -> T
    ) = MixSource(
        transform = transform,
        source = MixSource.Source.Just(source = this),
        mix = mix,
        scope = this@SummerExecutor
    )

    /**
     * @return [MixSource] that mixes [this] with [mix].
     * Triggers every times when executed or
     * [mix] produces new value (but only after being called at least once)
     */
    fun <TSourceEntity, TSourceParams, TMixEntity, TMixParams, T> MixSource<TSourceEntity, Any?, TMixEntity, TSourceParams>.mix(
        mix: SummerReducer<TMixEntity, TMixParams>,
        transform: (TSourceEntity, TMixEntity) -> T
    ) = MixSource(
        transform = transform,
        source = MixSource.Source.Mix(this),
        mix = mix,
        scope = this@SummerExecutor
    )
}

interface LifecycleSummerExecutor : SummerExecutor{
    /**
     * Must be called when receiver of execution result was created.
     * [SummerPresenter] is receiver by default in this library
     */
    fun receiverCreated()

    /**
     * Must be called when receiver of execution result was destroyed.
     * [SummerPresenter] is receiver by default in this library
     */
    fun receiverDestroyed()
}

@Suppress("FunctionName")
fun SummerExecutor(
    uiContext: CoroutineContext,
    defaultWorkContext: CoroutineContext,
    loggerFactory: SummerLogger.Factory
) : LifecycleSummerExecutor = DefaultSummerExecutor(
    uiContext, defaultWorkContext, loggerFactory
)

class DefaultSummerExecutor(
    mainContext: CoroutineContext,
    override val defaultWorkContext: CoroutineContext,
    loggerFactory: SummerLogger.Factory
) : CoroutineScope, LifecycleSummerExecutor {

    private val logger = loggerFactory.get(this::class)

    /**
     * Must be called when receiver of execution result was created.
     * [SummerPresenter] is receiver by default in this library
     */
    override fun receiverCreated() {
        subscriptions.forEach { it.subscribe() }
    }

    /**
     * Must be called when receiver of execution result was destroyed.
     * [SummerPresenter] is receiver by default in this library
     */
    override fun receiverDestroyed() {
        subscriptions.forEach { it.unsubscribe() }
        subscriptions.clear()
        job.cancel()
    }

    override fun onFailure(e: Throwable) {
        logger.error(e)
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        this@DefaultSummerExecutor.onFailure(e)
    }

    private val job = SupervisorJob()
    override val coroutineContext = mainContext + job + coroutineExceptionHandler

    // coroutineContext is final and initialized earlier
    // than passing this as CoroutineScope in DeferredExecutor
    private val executionManager = DeferredExecutor(uiScope = this)

    override fun <TEntity, TParams> SummerSource<TEntity, TParams>.executor(
        interceptor: SummerExecutorInterceptor<TEntity, TParams>,
        workContext: CoroutineContext,
        onExecute: suspend (_: TParams) -> Unit,
        onFailure: suspend (Throwable, _: TParams) -> Unit,
        onCancel: suspend (TParams) -> Unit,
        onSuccess: suspend (TEntity, _: TParams) -> Unit
    ): SourceExecutor<TEntity, TParams> = SourceExecutor(
        source = this,
        deferredExecutor = executionManager,
        workContext = workContext,
        interceptor = interceptor,
        onExecute = onExecute,
        onFailure = onFailure,
        onCancel = onCancel,
        onSuccess = onSuccess,
        scope = this@DefaultSummerExecutor
    )

    private class Subscription<TEntity, TParams>(
        private val source: SummerReducer<TEntity, TParams>,
        private val observer: SummerReducer.Observer<TEntity, TParams>
    ) {
        fun subscribe() {
            source.observe(observer)
        }

        fun unsubscribe() {
            source.unsubscribe(observer)
        }
    }

    private val subscriptions = mutableListOf<Subscription<*, *>>()

    override fun <TEntity, TParams> SummerReducer<TEntity, TParams>.executor(
        interceptor: SummerExecutorInterceptor<TEntity, TParams?>,
        workContext: CoroutineContext,
        onExecute: suspend (_: TParams?) -> Unit,
        onFailure: suspend (Throwable, _: TParams?) -> Unit,
        onCancel: suspend (TParams?) -> Unit,
        onSuccess: suspend (TEntity, _: TParams?) -> Unit
    ): ReducerExecutor<TEntity, TParams> = ReducerExecutor(
        source = this,
        deferredExecutor = executionManager,
        workContext = workContext,
        interceptor = interceptor,
        onExecute = onExecute,
        onFailure = onFailure,
        onCancel = onCancel,
        onSuccess = onSuccess,
        scope = this@DefaultSummerExecutor
    ).also { sharedSourceExecutor ->
        subscriptions += Subscription(this, sharedSourceExecutor)
    }

    override fun <T, TSourceEntity, TMixEntity, TSourceParams> MixSource<T, TSourceEntity, TMixEntity, TSourceParams>.executor(
        interceptor: SummerExecutorInterceptor<T, TSourceParams>,
        onExecute: suspend (_: TSourceParams) -> Unit,
        onFailure: suspend (Throwable, _: TSourceParams) -> Unit,
        onCancel: suspend (TSourceParams) -> Unit,
        onSuccess: suspend (T, TSourceParams) -> Unit
    ): MixSourceExecutor<T, TSourceParams> = MixSourceExecutor(
        source = this,
        deferredExecutor = executionManager,
        interceptor = interceptor,
        onExecute = onExecute,
        onFailure = onFailure,
        onCancel = onCancel,
        onSuccess = onSuccess,
        scope = this@DefaultSummerExecutor,
        workContext = defaultWorkContext
    ).also { mixSourceExecutor ->
        this.consumer = mixSourceExecutor
        subscriptions += Subscription(this.mix, this)
    }
}