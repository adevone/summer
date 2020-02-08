package summer

import summer.execution.SummerExecutor
import summer.store.GetMirrorProperty
import summer.store.InMemoryStore
import summer.store.SummerStore
import summer.store.SummerStoresController
import summer.store.SummerViewStateProxyProvider
import kotlin.coroutines.CoroutineContext

/**
 * Base presenter. Allows to restore view state and
 * execute summer sources ([summer.execution.source.SummerSource],
 * [summer.execution.reducer.SummerReducer] or [summer.execution.mix.MixSource])
 *
 * Should not be used as direct parent of feature presenters.
 * You should create your own base presenter in your project.
 */
abstract class SummerPresenter<TViewState, TViewMethods>(
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
), SummerViewStateProxyProvider<TViewState>, UnsafePresenterLifecycleOwner {

    private val storesController = SummerStoresController<TViewState, TViewMethods>()

    protected val viewMethods: TViewMethods?
        get() = storesController.viewMethods

    /**
     * Must be called when view is created. May be called multiple times
     */
    fun viewCreated(
        viewState: TViewState,
        viewMethods: TViewMethods
    ) {
        storesController.viewCreated(
            viewState,
            viewMethods
        )
    }

    override fun viewCreatedUnsafe(
        viewState: Any,
        viewMethods: Any
    ) {
        @Suppress("UNCHECKED_CAST")
        viewCreated(
            viewState as TViewState,
            viewMethods as TViewMethods
        )
    }

    override fun viewDestroyed() {
        storesController.viewDestroyed()
    }

    /**
     * Shorthand for [storeIn] with [localStore] of this presenter
     */
    protected fun <T> store(
        getMirrorProperty: GetMirrorProperty<TViewState, T>? = null,
        initial: T
    ) = storeIn(
        getMirrorProperty = getMirrorProperty,
        initial = initial,
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
        getMirrorProperty: GetMirrorProperty<TViewState, T>? = null,
        initial: T,
        store: SummerStore
    ) = storesController.storeIn(
        getMirrorProperty = getMirrorProperty,
        initial = initial,
        store = store
    )

    override fun created() {
        super.receiverCreated()
    }

    override fun destroyed() {
        super.receiverDestroyed()
    }

    override fun entered() {
        onEnter()
    }

    override  fun exited() {
        onExit()
    }

    override fun appeared() {
        onAppear()
    }

    override fun disappeared() {
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

    /**
     * Convenient constructor if IoC container used
     */
    constructor(dependencies: Dependencies) : this(
        uiContext = dependencies.uiContext,
        defaultWorkContext = dependencies.workContext,
        loggerFactory = dependencies.loggerFactory,
        localStore = dependencies.localStore
    )

    class Dependencies(
        val uiContext: CoroutineContext = defaultUiContext,
        val workContext: CoroutineContext = defaultBackgroundContext,
        val loggerFactory: SummerLogger.Factory = DefaultLoggerFactory,
        val localStore: SummerStore = InMemoryStore()
    )
}

/**
 * Non-generic protocol that can be used to call lifecycle events of [SummerPresenterWithRouter]
 * in languages without covariant types support (like Swift)
 */
interface UnsafePresenterLifecycleOwner {

    /**
     * Same as [SummerPresenter.viewCreated] but with unsafe typecast.
     * Used when view can not pass typed [viewState] and [viewMethods]
     */
    fun viewCreatedUnsafe(viewState: Any, viewMethods: Any)

    /**
     * Must be called when view is destroyed. May be called multiple times
     */
    fun viewDestroyed()

    /**
     * Must be called when presenter is destroyed. Must be called exactly once
     */
    fun destroyed()

    /**
     * Must be called when presenter is created. Must be called exactly once
     */
    fun created()

    /**
     * Must be called when user sees view for the first time
     */
    fun entered()

    /**
     * Must be called every time when view appears (see [SummerPresenter.onAppear])
     */
    fun appeared()

    /**
     * Must be called every time when view disappears (see [SummerPresenter.onDisappear])
     */
    fun disappeared()

    /**
     * Must be called when view popped from stack
     */
    fun exited()
}