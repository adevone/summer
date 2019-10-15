package summer

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import summer.execution.DeferredExecutor
import summer.execution.LoadingExecutorInterceptor
import summer.execution.NoInterceptor
import summer.execution.SummerExecutorInterceptor
import summer.execution.mix.MixSource
import summer.execution.mix.MixSourceExecutor
import summer.execution.reducer.ReducerExecutor
import summer.execution.reducer.SummerReducer
import summer.execution.source.SourceExecutor
import summer.execution.source.SummerSource
import summer.store.SummerStoresController
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty0

abstract class SummerPresenter<
        TViewState,
        TViewMethods,
        TRouter>(
    private val localStore: SummerStore,
    private val workContext: CoroutineContext,
    uiContext: CoroutineContext,
    loggerFactory: SummerLogger.Factory
) : CoroutineScope {

    private val logger = loggerFactory.get(this::class)
    private val storesController = SummerStoresController()

    protected abstract fun createViewStateProxy(vs: TViewState): TViewState

    private var _viewStateProxy: TViewState? = null
    protected val viewStateProxy: TViewState get() = _viewStateProxy!!

    protected var viewMethods: TViewMethods? = null

    private var _router: TRouter? = null
    protected val router: TRouter get() = _router!!

    fun created() {
        subscriptions.forEach { it.subscribe() }
    }

    fun destroyed() {
        storesController.onDestroy()
        subscriptions.forEach { it.unsubscribe() }
        subscriptions.clear()
        job.cancel()
    }

    fun viewCreated(
        viewState: TViewState,
        viewMethods: TViewMethods,
        router: TRouter
    ) {
        this._viewStateProxy = createViewStateProxy(viewState)
        this.viewMethods = viewMethods
        this._router = router

        // onConnect call placed there because presenter methods may be called in initView.
        // Presenter must be initialized at that moment
        storesController.onMirrorConnect()
    }

    fun viewDestroyed() {
        storesController.onMirrorDisconnect()
        this.viewMethods = null
        this._router = null
    }

    protected fun <T> store(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T
    ) = storeIn(
        viewStateProperty = viewStateProperty,
        initialValue = initialValue,
        store = localStore
    )

    protected fun <T> storeIn(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T,
        store: SummerStore
    ) = storesController.storeIn(
        mirrorProperty = viewStateProperty,
        initialValue = initialValue,
        store = store
    )

    fun entered() {
        onEnter()
    }

    fun appeared() {
        onAppear()
    }

    fun disappeared() {
        onDisappear()
    }

    fun exited() {
        onExit()
    }

    fun <TEntity, TParams> loadingInterceptor(
        getProperty: suspend () -> KMutableProperty0<Boolean>,
        needShow: suspend (event: SummerExecutorInterceptor.Event.Executed<TEntity, TParams>) -> Boolean = { true }
    ): LoadingExecutorInterceptor<TEntity, TParams> = LoadingExecutorInterceptor(
        getProperty = getProperty,
        needShow = needShow
    )

    protected open fun onEnter() {}

    protected open fun onExit() {}

    protected open fun onAppear() {}

    protected open fun onDisappear() {}

    protected open fun onError(e: Throwable) {
        logger.error(e)
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        this@SummerPresenter.onError(e)
    }

    private val job = SupervisorJob()
    final override val coroutineContext = uiContext + job + coroutineExceptionHandler

    // coroutineContext is final and initialized earlier
    // than passing this as CoroutineScope in DeferredExecutor
    @Suppress("LeakingThis")
    private val executionManager = DeferredExecutor(uiScope = this)

    fun <TSourceEntity, TSourceParams, TMixEntity, TMixParams, T> SummerSource<TSourceEntity, TSourceParams>.mix(
        mix: SummerReducer<TMixEntity, TMixParams>,
        transform: (TSourceEntity, TMixEntity) -> T
    ) = MixSource(
        transform = transform,
        source = MixSource.Source.Just(source = this),
        mix = mix,
        scope = this@SummerPresenter
    )

    fun <TSourceEntity, TSourceParams, TMixEntity, TMixParams, T> MixSource<TSourceEntity, Any?, TMixEntity, TSourceParams>.mix(
        mix: SummerReducer<TMixEntity, TMixParams>,
        transform: (TSourceEntity, TMixEntity) -> T
    ) = MixSource(
        transform = transform,
        source = MixSource.Source.Mix(this),
        mix = mix,
        scope = this@SummerPresenter
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
        scope = this@SummerPresenter,
        workContext = workContext
    ).also { sharedSourceExecutor ->
        subscriptions += Subscription(this, sharedSourceExecutor)
    }

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
        scope = this@SummerPresenter,
        workContext = workContext
    ).also { mixSourceExecutor ->
        this.consumer = mixSourceExecutor
        subscriptions += Subscription(this.mix, this)
    }

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
        scope = this@SummerPresenter,
        workContext = workContext
    )

    fun <TEntity> SourceExecutor<TEntity, Unit>.execute() = execute(Unit)
    fun <TEntity> ReducerExecutor<TEntity, Unit>.execute() = execute(Unit)
    fun <TEntity> MixSourceExecutor<TEntity, Unit>.execute() = execute(Unit)
}