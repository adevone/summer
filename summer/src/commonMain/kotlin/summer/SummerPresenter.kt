package summer

import kotlinx.coroutines.*
import summer.summer.*
import summer.log.logFor
import summer.log.tagByClass
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty0

abstract class SummerPresenter<
        TViewState,
        TViewMethods,
        TRouter>(
    private val exceptionsHandler: ExceptionsHandler,
    private val stateHolder: StateHolder,
    workContext: CoroutineContext,
    private val uiContext: CoroutineContext
) {
    protected abstract fun createViewStateProxy(vs: TViewState): TViewState

    private var _viewStateProxy: TViewState? = null
    protected val viewStateProxy: TViewState get() = _viewStateProxy!!

    protected var _viewMethods: TViewMethods? = null
    protected val viewMethods: TViewMethods get() = _viewMethods!!

    protected var _router: TRouter? = null
    protected val router: TRouter get() = _router!!

    private lateinit var owner: Any

    private var isOwnerAlreadyPresent = false
    fun beforeCreateView(owner: Any) {
        isOwnerAlreadyPresent = stateHolder.isPresent(owner)
    }

    fun onCreateView(
        viewState: TViewState,
        viewMethods: TViewMethods,
        router: TRouter,
        owner: Any
    ) {
        this.owner = owner
        this._viewStateProxy = createViewStateProxy(viewState)
        this._viewMethods = viewMethods
        this._router = router

        // Некоторые проперти при изменении значений обращаются к presenter'у.
        // Поэтому presenter должен быть инициализирован в момент их установки
        stateHolder.restoreProperties(this)
        subscriptions.forEach { it.subscribe() }
        if (!isOwnerAlreadyPresent) {
            onEnter()
        }
    }

    private var isDestroyed = false
    fun onDestroyView() {
        isDestroyed = true
        subscriptions.forEach { it.unsubscribe() }
        subscriptions.clear()
        job.cancelChildren()
    }

    fun onDestroyOwner() {
        stateHolder.onDestroyOwner(owner)
    }

    protected fun <T> storeInSession(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T
    ): StoredProperty<T> = store(
        viewStateProperty,
        getOwner = { this::class },
        initialValue = initialValue
    )

    protected fun <T> store(
        viewStateProperty: KMutableProperty0<T>? = null,
        getOwner: () -> Any = { owner },
        initialValue: T
    ): StoredProperty<T> = stateHolder.store(
        getKey = getOwner,
        isPresenterDestroyed = { isDestroyed },
        presenter = this,
        viewStateProperty = viewStateProperty,
        initialValue = initialValue
    )

    protected open fun onEnter() {}

    open fun onPop() {}

    open fun onAppear() {}

    open fun onDisappear() {}

    protected open fun onError(e: Throwable) {
        logger.error(e)
        throw e
    }

    protected fun <TSourceEntity, TSourceParams, TMixEntity, TMixParams, T> SummerSource<TSourceEntity, TSourceParams>.mix(
        mix: SummerSharedSource<TMixEntity, TMixParams>,
        transform: (TSourceEntity, TMixEntity) -> T
    ) = MixSource(
        transform = transform,
        source = MixSource.Source.Just(source = this),
        mix = mix,
        scope = scope
    )

    protected fun <TSourceEntity, TSourceParams, TMixEntity, TMixParams, T> MixSource<TSourceEntity, Any?, TMixEntity, TSourceParams>.mix(
        mix: SummerSharedSource<TMixEntity, TMixParams>,
        transform: (TSourceEntity, TMixEntity) -> T
    ) = MixSource(
        transform = transform,
        source = MixSource.Source.Mix(this),
        mix = mix,
        scope = scope
    )

    private class Subscription<TEntity, TParams>(
        private val source: SummerSharedSource<TEntity, TParams>,
        private val observer: SummerSharedSource.Observer<TEntity, TParams>
    ) {
        fun subscribe() {
            source.observe(observer)
        }

        fun unsubscribe() {
            source.unsubscribe(observer)
        }
    }

    private val subscriptions = mutableListOf<Subscription<*, *>>()

    protected fun <TEntity, TParams> SummerSharedSource<TEntity, TParams>.executor(
        getLoadingProperty: (() -> KMutableProperty0<Boolean>)? = null,
        needShowLoading: suspend () -> Boolean = { true },
        onError: suspend (Throwable) -> Unit = { e -> throw e },
        onComplete: suspend (TEntity, _: TParams?) -> Unit = { _, _ -> }
    ): SharedSourceExecutor<TEntity, TParams> = SharedSourceExecutor(
        source = this,
        action = { deferred, params ->
            handleDeferred(
                deferred = deferred,
                onComplete = onComplete,
                params = params,
                onError = onError,
                getLoadingProperty = getLoadingProperty,
                needShowLoading = needShowLoading
            )
        },
        scope = scope,
        uiContext = uiContext
    ).also { sharedSourceExecutor ->
        subscriptions += Subscription(this, sharedSourceExecutor)
    }

    protected fun <T, TSourceEntity, TMixEntity, TSourceParams> MixSource<T, TSourceEntity, TMixEntity, TSourceParams>.executor(
        getLoadingProperty: (() -> KMutableProperty0<Boolean>)? = null,
        needShowLoading: suspend () -> Boolean = { true },
        onError: suspend (Throwable) -> Unit = { e -> throw e },
        onComplete: suspend (T, TSourceParams) -> Unit = { _, _ -> }
    ): MixSourceExecutor<T, TSourceParams> = MixSourceExecutor(
        source = this,
        action = { deferred, params ->
            handleDeferred(
                deferred = deferred,
                onComplete = onComplete,
                params = params,
                onError = onError,
                getLoadingProperty = getLoadingProperty,
                needShowLoading = needShowLoading
            )
        },
        uiContext = uiContext
    ).also { mixSourceExecutor ->
        this.consumer = mixSourceExecutor
        subscriptions += Subscription(this.mix, this)
    }

    protected fun <TEntity, TParams> SummerSource<TEntity, TParams>.executor(
        getLoadingProperty: (() -> KMutableProperty0<Boolean>)? = null,
        needShowLoading: suspend () -> Boolean = { true },
        onError: suspend (Throwable) -> Unit = { e -> throw e },
        onComplete: suspend (TEntity, _: TParams) -> Unit = { _, _ -> }
    ): SourceExecutor<TEntity, TParams> = SourceExecutor(
        source = this,
        action = { deferred, params ->
            handleDeferred(
                deferred = deferred,
                onComplete = onComplete,
                params = params,
                onError = onError,
                getLoadingProperty = getLoadingProperty,
                needShowLoading = needShowLoading
            )
        },
        scope = scope,
        uiContext = uiContext
    )

    protected suspend fun <TEntity, TParams> handleDeferred(
        deferred: Deferred<TEntity>,
        params: TParams,
        onComplete: suspend (TEntity, TParams) -> Unit = { _, _ -> },
        onError: suspend (Throwable) -> Unit,
        getLoadingProperty: (() -> KMutableProperty0<Boolean>)? = null,
        needShowLoading: suspend () -> Boolean = { true }
    ) {
        try {
            val result = if (getLoadingProperty != null) {
                val isLoadingShowedProperty = getLoadingProperty()
                val isLoadingShowed = needShowLoading()
                isLoadingShowedProperty.set(isLoadingShowed)
                try {
                    deferred.await()
                } finally {
                    isLoadingShowedProperty.set(false)
                }
            } else {
                deferred.await()
            }
            onComplete(result, params)
        } catch (e: CancellationException) {
            logger.info { "$this cancelled" }
        } catch (e: Throwable) {
            try {
                onError(e)
            } catch (e: Throwable) {
                try {
                    this@SummerPresenter.onError(e)
                } catch (e: Throwable) {
                    exceptionsHandler.handle(e)
                }
            }
        }
    }

    suspend fun <TEntity> SummerSource<TEntity, Unit>.execute() = execute(Unit)

    private val job = SupervisorJob()
    private val scope = CoroutineScope(workContext + job)

    protected open val tag: String = tagByClass(this::class)

    protected val logger by lazy { logFor(tag) }
}