package summer

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty0

abstract class SummerPresenter<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any>(
    private val exceptionsHandler: ExceptionsHandler,
    private val localStore: SummerStore,
    private val workContext: CoroutineContext,
    uiContext: CoroutineContext,
    loggerFactory: SummerLogger.Factory
) : CoroutineScope {

    private val logger = loggerFactory.get(this::class)

    protected abstract fun createViewStateProxy(vs: TViewState): TViewState

    private var _viewStateProxy: TViewState? = null
    protected val viewStateProxy: TViewState get() = _viewStateProxy!!

    private var _viewMethods: TViewMethods? = null
    protected val viewMethods: TViewMethods get() = _viewMethods!!

    private var _router: TRouter? = null
    protected val router: TRouter get() = _router!!

    fun onCreate() {
        subscriptions.forEach { it.subscribe() }
    }

    private var isDestroyed = false
    fun onDestroy() {
        isDestroyed = true
        subscriptions.forEach { it.unsubscribe() }
        subscriptions.clear()
        job.cancel()
    }

    fun onCreateView(
        viewState: TViewState,
        viewMethods: TViewMethods,
        router: TRouter
    ) {
        this._viewStateProxy = createViewStateProxy(viewState)
        this._viewMethods = viewMethods
        this._router = router

        // Placed there because presenter methods may be called in initView.
        // Presenter must be initialized at that moment
        stores.forEach { it.restore() }
    }

    fun onDestroyView() {
        this._viewStateProxy = null
        this._viewMethods = null
        this._router = null
    }

    protected fun <TEntity, TParams> loadingInterceptor(
        getProperty: suspend () -> KMutableProperty0<Boolean>,
        needShow: suspend (event: SummerExecutorInterceptor.Event.Executed<TEntity, TParams>) -> Boolean = { true }
    ): LoadingExecutorInterceptor<TEntity, TParams> = LoadingExecutorInterceptor(
        getProperty = getProperty,
        needShow = needShow
    )

    protected fun <T> store(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T
    ) = storeIn(
        viewStateProperty,
        initialValue,
        localStore
    )

    private val stores = mutableSetOf<SummerStore>()
    protected fun <T> storeIn(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T,
        store: SummerStore
    ) = store.store(
        onSet = { value ->
            if (!isDestroyed) {
                viewStateProperty?.set(value)
            }
        },
        initialValue = initialValue
    ).also {
        stores.add(store)
    }

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

    protected open fun onEnter() {}

    protected open fun onExit() {}

    protected open fun onAppear() {}

    protected open fun onDisappear() {}

    protected open fun onError(e: Throwable) {
        logger.error(e)
        throw e
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        try {
            this@SummerPresenter.onError(e)
        } catch (e: Throwable) {
            exceptionsHandler.handle(e)
        }
    }

    private val job = SupervisorJob()
    final override val coroutineContext = uiContext + job + coroutineExceptionHandler

    // coroutineContext is final and initialized earlier
    // than passing this as CoroutineScope in ExecutionManager
    @Suppress("LeakingThis")
    private val executionManager = ExecutionManager(logger, uiScope = this)

    protected fun <TSourceEntity, TSourceParams, TMixEntity, TMixParams, T> SummerSource<TSourceEntity, TSourceParams>.mix(
        mix: SummerReducer<TMixEntity, TMixParams>,
        transform: (TSourceEntity, TMixEntity) -> T
    ) = MixSource(
        transform = transform,
        source = MixSource.Source.Just(source = this),
        mix = mix,
        scope = this@SummerPresenter
    )

    protected fun <TSourceEntity, TSourceParams, TMixEntity, TMixParams, T> MixSource<TSourceEntity, Any?, TMixEntity, TSourceParams>.mix(
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

    protected fun <TEntity, TParams> SummerReducer<TEntity, TParams>.executor(
        interceptor: SummerExecutorInterceptor<TEntity, TParams?> = NoInterceptor(),
        onExecute: suspend (_: TParams?) -> Unit = { _ -> },
        onFailure: suspend (Throwable, _: TParams?) -> Unit = { e, _ -> throw e },
        onSuccess: suspend (TEntity, _: TParams?) -> Unit = { _, _ -> }
    ): ReducerExecutor<TEntity, TParams> = ReducerExecutor(
        source = this,
        executionManager = executionManager,
        interceptor = interceptor,
        onExecute = onExecute,
        onFailure = onFailure,
        onSuccess = onSuccess,
        scope = this@SummerPresenter,
        workContext = workContext
    ).also { sharedSourceExecutor ->
        subscriptions += Subscription(this, sharedSourceExecutor)
    }

    protected fun <T, TSourceEntity, TMixEntity, TSourceParams> MixSource<T, TSourceEntity, TMixEntity, TSourceParams>.executor(
        interceptor: SummerExecutorInterceptor<T, TSourceParams> = NoInterceptor(),
        onExecute: suspend (_: TSourceParams) -> Unit = { _ -> },
        onFailure: suspend (Throwable, _: TSourceParams) -> Unit = { e, _ -> throw e },
        onSuccess: suspend (T, TSourceParams) -> Unit = { _, _ -> }
    ): MixSourceExecutor<T, TSourceParams> = MixSourceExecutor(
        source = this,
        executionManager = executionManager,
        interceptor = interceptor,
        onExecute = onExecute,
        onFailure = onFailure,
        onSuccess = onSuccess,
        scope = this@SummerPresenter,
        workContext = workContext
    ).also { mixSourceExecutor ->
        this.consumer = mixSourceExecutor
        subscriptions += Subscription(this.mix, this)
    }

    protected fun <TEntity, TParams> SummerSource<TEntity, TParams>.executor(
        interceptor: SummerExecutorInterceptor<TEntity, TParams> = NoInterceptor(),
        onExecute: suspend (_: TParams) -> Unit = { _ -> },
        onFailure: suspend (Throwable, _: TParams) -> Unit = { e, _ -> throw e },
        onSuccess: suspend (TEntity, _: TParams) -> Unit = { _, _ -> }
    ): SourceExecutor<TEntity, TParams> = SourceExecutor(
        source = this,
        executionManager = executionManager,
        interceptor = interceptor,
        onExecute = onExecute,
        onFailure = onFailure,
        onSuccess = onSuccess,
        scope = this@SummerPresenter,
        workContext = workContext
    )

    fun <TEntity> SourceExecutor<TEntity, Unit>.execute() = execute(Unit)
    fun <TEntity> ReducerExecutor<TEntity, Unit>.execute() = execute(Unit)
    fun <TEntity> MixSourceExecutor<TEntity, Unit>.execute() = execute(Unit)
}