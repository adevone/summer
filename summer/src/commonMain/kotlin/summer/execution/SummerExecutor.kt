package summer.execution

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import summer.SummerLogger
import summer.SummerPresenter
import summer.execution.mix.MixSource
import summer.execution.mix.MixSourceExecutor
import summer.execution.reducer.ReducerExecutor
import summer.execution.reducer.SummerReducer
import summer.execution.source.SourceExecutor
import summer.execution.source.SummerSource
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty0

/**
 * Manages executions of sources ([SummerSource], [SummerReducer], [MixSource])
 */
abstract class SummerExecutor(
    mainContext: CoroutineContext,
    private val workContext: CoroutineContext,
    loggerFactory: SummerLogger.Factory
) : CoroutineScope {

    private val logger = loggerFactory.get(this::class)

    /**
     * Must be called when receiver of execution result was created.
     * [SummerPresenter] is receiver by default in this library
     */
    fun receiverCreated() {
        subscriptions.forEach { it.subscribe() }
    }

    /**
     * Must be called when receiver of execution result was destroyed.
     * [SummerPresenter] is receiver by default in this library
     */
    fun receiverDestroyed() {
        subscriptions.forEach { it.unsubscribe() }
        subscriptions.clear()
        job.cancel()
    }

    /**
     * Called on each unhandled error in any executor
     */
    protected open fun onFailure(e: Throwable) {
        logger.error(e)
    }

    /**
     * Factory method for [LoadingExecutorInterceptor]
     */
    fun <TEntity, TParams> loadingInterceptor(
        getProperty: suspend () -> KMutableProperty0<Boolean>,
        needShow: suspend (event: SummerExecutorInterceptor.Event.Executed<TEntity, TParams>) -> Boolean = { true }
    ): LoadingExecutorInterceptor<TEntity, TParams> = LoadingExecutorInterceptor(
        getProperty = getProperty,
        needShow = needShow
    )

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        this@SummerExecutor.onFailure(e)
    }

    private val job = SupervisorJob()
    final override val coroutineContext = mainContext + job + coroutineExceptionHandler

    // coroutineContext is final and initialized earlier
    // than passing this as CoroutineScope in DeferredExecutor
    @Suppress("LeakingThis")
    private val executionManager = DeferredExecutor(uiScope = this)

    /**
     * Factory method for [SourceExecutor]
     */
    fun <TEntity, TParams> SummerSource<TEntity, TParams>.executor(
        interceptor: SummerExecutorInterceptor<TEntity, TParams> = NoInterceptor(),
        onExecute: suspend (_: TParams) -> Unit = { _ -> },
        onFailure: suspend (Throwable, _: TParams) -> Unit = { e, _ -> throw e },
        onCancel: suspend (TParams) -> Unit = { params -> logger.info { "$this cancelled, params=$params " } },
        onSuccess: suspend (TEntity, _: TParams) -> Unit = { _, _ -> }
    ): SourceExecutor<TEntity, TParams> = SourceExecutor(
        source = this,
        deferredExecutor = executionManager,
        interceptor = interceptor,
        onExecute = onExecute,
        onFailure = onFailure,
        onCancel = onCancel,
        onSuccess = onSuccess,
        scope = this@SummerExecutor,
        workContext = workContext
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

    /**
     * Factory method for [ReducerExecutor]
     */
    fun <TEntity, TParams> SummerReducer<TEntity, TParams>.executor(
        interceptor: SummerExecutorInterceptor<TEntity, TParams?> = NoInterceptor(),
        onExecute: suspend (_: TParams?) -> Unit = { _ -> },
        onFailure: suspend (Throwable, _: TParams?) -> Unit = { e, _ -> throw e },
        onCancel: suspend (TParams?) -> Unit = { params -> logger.info { "$this cancelled, params=$params" } },
        onSuccess: suspend (TEntity, _: TParams?) -> Unit = { _, _ -> }
    ): ReducerExecutor<TEntity, TParams> = ReducerExecutor(
        source = this,
        deferredExecutor = executionManager,
        interceptor = interceptor,
        onExecute = onExecute,
        onFailure = onFailure,
        onCancel = onCancel,
        onSuccess = onSuccess,
        scope = this@SummerExecutor,
        workContext = workContext
    ).also { sharedSourceExecutor ->
        subscriptions += Subscription(this, sharedSourceExecutor)
    }

    /**
     * Factory method for [MixSourceExecutor]
     */
    fun <T, TSourceEntity, TMixEntity, TSourceParams> MixSource<T, TSourceEntity, TMixEntity, TSourceParams>.executor(
        interceptor: SummerExecutorInterceptor<T, TSourceParams> = NoInterceptor(),
        onExecute: suspend (_: TSourceParams) -> Unit = { _ -> },
        onFailure: suspend (Throwable, _: TSourceParams) -> Unit = { e, _ -> throw e },
        onCancel: suspend (TSourceParams) -> Unit = { sourceParams -> logger.info { "$this cancelled, sourceParams=$sourceParams" } },
        onSuccess: suspend (T, TSourceParams) -> Unit = { _, _ -> }
    ): MixSourceExecutor<T, TSourceParams> = MixSourceExecutor(
        source = this,
        deferredExecutor = executionManager,
        interceptor = interceptor,
        onExecute = onExecute,
        onFailure = onFailure,
        onCancel = onCancel,
        onSuccess = onSuccess,
        scope = this@SummerExecutor,
        workContext = workContext
    ).also { mixSourceExecutor ->
        this.consumer = mixSourceExecutor
        subscriptions += Subscription(this.mix, this)
    }

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

    /**
     * Shorthand for [SourceExecutor.execute] when SourceExecutor.TParams is Unit
     */
    fun <TEntity> SourceExecutor<TEntity, Unit>.execute() = execute(Unit)

    /**
     * Shorthand for [ReducerExecutor.execute] when ReducerExecutor.TParams is Unit
     */
    fun <TEntity> ReducerExecutor<TEntity, Unit>.execute() = execute(Unit)

    /**
     * Shorthand for [MixSourceExecutor.execute] when MixSourceExecutor.TSourceParams is Unit
     */
    fun <TEntity> MixSourceExecutor<TEntity, Unit>.execute() = execute(Unit)
}