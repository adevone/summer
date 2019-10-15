package summer

import summer.execution.SummerExecutor
import summer.store.InMemoryStore
import summer.store.SummerStore
import summer.store.SummerStoresController
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty0

/**
 * Base presenter. Allows to restore view state and
 * execute summer sources ([summer.execution.source.SummerSource],
 * [summer.execution.reducer.SummerReducer] or [summer.execution.mix.MixSource])
 *
 * Should not be used as direct parent of feature presenters.
 * You should create your own base presenter in your project.
 */
abstract class SummerPresenter<
        TViewState,
        TViewMethods,
        TRouter>(
    uiContext: CoroutineContext = defaultUiContext,
    defaultWorkContext: CoroutineContext = defaultBackgroundContext,
    loggerFactory: SummerLogger.Factory = DefaultLoggerFactory,
    /**
     * Store created specifically for this presenter. Must not be reused
     */
    private val localStore: SummerStore = InMemoryStore()
) : SummerExecutor(
    mainContext = uiContext,
    defaultWorkContext = defaultWorkContext,
    loggerFactory = loggerFactory
) {
    constructor(dependencies: Dependencies) : this(
        uiContext = dependencies.uiContext,
        defaultWorkContext = dependencies.workContext,
        loggerFactory = dependencies.loggerFactory,
        localStore = dependencies.localStore
    )

    /**
     * Convenient form of arguments if some kind on IoC container used
     */
    class Dependencies(
        val uiContext: CoroutineContext = defaultUiContext,
        val workContext: CoroutineContext = defaultBackgroundContext,
        val loggerFactory: SummerLogger.Factory = DefaultLoggerFactory,
        val localStore: SummerStore = InMemoryStore()
    )

    private val storesController = SummerStoresController()

    /**
     * Create proxy for view state. Proxy must contain all properties defined in TViewState.
     * Properties of proxy must use delegates created by [store] of [storeIn] methods
     *
     * You can use summer plugin to make overriding of this method easier
     *
     * Example:
     *
     * object FeatureView {
     *
     *     interface State {
     *         var prop: Int
     *     }
     * }
     *
     * override fun createViewStateProxy(vs: FeatureView.State) = object : FeatureView.State {
     *     override var prop by store(vs::prop, initialValue = 0)
     * }
     */
    protected abstract fun createViewStateProxy(vs: TViewState): TViewState

    private var _viewStateProxy: TViewState? = null
    protected val viewStateProxy: TViewState get() = _viewStateProxy!!

    protected var viewMethods: TViewMethods? = null

    private var _router: TRouter? = null
    protected val router: TRouter get() = _router!!

    /**
     * Must be called when presenter is created. Must be called exactly once
     */
    fun created() {
        receiverCreated()
    }

    /**
     * Must be called when presenter is destroyed. Must be called exactly once
     */
    fun destroyed() {
        receiverDestroyed()
    }

    /**
     * Must be called when view is created. May be called multiple times
     */
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

    /**
     * Must be called when view is destroyed. May be called multiple times
     */
    fun viewDestroyed() {
        storesController.onMirrorDisconnect()
        this.viewMethods = null
        this._router = null
    }

    /**
     * Shorthand for [storeIn] with [localStore] of this presenter
     */
    protected fun <T> store(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T
    ) = storeIn(
        viewStateProperty = viewStateProperty,
        initialValue = initialValue,
        store = localStore
    )

    /**
     * Create delegate for property stored in any store
     *
     * May be called in createViewStateProxy method or just
     * in presenter if some sort of persistent store is used.
     *
     * If viewStateProperty is not null value will be stored in store
     * and mirrored in viewStateProperty if view is not destroyed
     *
     * If viewStateProperty is null value will be stored only in store
     *
     * @return stored property delegate
     */
    protected fun <T> storeIn(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T,
        store: SummerStore
    ) = storesController.storeIn(
        mirrorProperty = viewStateProperty,
        initialValue = initialValue,
        store = store
    )

    /**
     * Must be called when user sees view for the first time
     */
    fun entered() {
        onEnter()
    }

    /**
     * Must be called when view popped from stack
     */
    fun exited() {
        onExit()
    }

    /**
     * Must be called every time when view appears (see [onAppear])
     */
    fun appeared() {
        onAppear()
    }

    /**
     * Must be called every time when view disappears (see [onDisappear])
     */
    fun disappeared() {
        onDisappear()
    }

    /**
     * Called when user sees view for the first time
     */
    protected open fun onEnter() {}

    /**
     * Called when view popped from stack
     */
    protected open fun onExit() {}

    /**
     * Called every time view appears.
     * It may happen when user opens view for the first time or switches to your app
     */
    protected open fun onAppear() {}

    /**
     * Called every time when view disappears.
     * It may happen when user pops view from stack or switches to another app
     */
    protected open fun onDisappear() {}
}