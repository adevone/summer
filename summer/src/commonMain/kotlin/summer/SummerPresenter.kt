package summer

import summer.events.DoExactlyOnceStrategy
import summer.events.DoOnlyWhenAttachedStrategy
import summer.events.EventFactory
import summer.events.SummerEvent
import summer.execution.SummerExecutor
import summer.store.InMemoryStore
import summer.store.SummerStore
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
abstract class SummerPresenter<TView>(
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
), SummerViewProxyProvider<TView>, UnsafePresenterLifecycleOwner,
    EventFactory<TView>,
    DoOnlyWhenAttachedStrategy.Factory<TView>,
    DoExactlyOnceStrategy.Factory<TView> {

    override var view: TView? = null

    /**
     * Must be called when view is created. May be called multiple times
     */
    fun viewCreated(view: TView) {
        this.view = view

        // restore call placed there because presenter methods may be called due view initialization.
        // restore must be called after initView
        stores.forEach { it.restore() }
        events.forEach { it.viewCreated(view) }
    }

    override fun viewCreatedUnsafe(view: Any) {
        @Suppress("UNCHECKED_CAST")
        viewCreated(view as TView)
    }

    override fun viewDestroyed() {
        events.forEach { it.viewDestroyed() }
        this.view = null
    }

    private val stores = mutableSetOf<SummerStore>()

    /**
     * Shorthand for [stateIn] with [localStore] of this presenter
     */
    protected fun <T> state(
        getMirrorProperty: GetMirrorProperty<TView, T>? = null,
        initial: T
    ) = stateIn(
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
    fun <T> stateIn(
        getMirrorProperty: GetMirrorProperty<TView, T>? = null,
        initial: T,
        store: SummerStore
    ): SummerStore.StateDelegate<T> {
        return store.createState(
            onSet = { value ->
                val currentViewState = view
                if (currentViewState != null && getMirrorProperty != null) {
                    val property = getMirrorProperty(currentViewState)
                    property.set(value)
                }
            },
            initial = initial
        ).also {
            stores.add(store)
        }
    }

    private val events = mutableListOf<SummerEvent<TView>>()

    override fun eventCreated(event: SummerEvent<TView>) {
        events.add(event)
    }

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
     * Used when view can not pass typed [view]
     */
    fun viewCreatedUnsafe(view: Any)

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

typealias GetMirrorProperty<TView, T> = (TView) -> KMutableProperty0<T>