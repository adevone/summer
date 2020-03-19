package summer

import summer.events.DoExactlyOnceStrategy
import summer.events.DoOnlyWhenAttachedStrategy
import summer.events.EventFactory
import summer.events.SummerEvent
import summer.store.InMemoryStore
import summer.store.SummerStore
import kotlin.reflect.KMutableProperty0

/**
 * Base presenter. Helps with view state restoring (see [SummerStore])
 * and executing of events (see [SummerEvent])
 */
abstract class SummerPresenter<TView> :
    UnsafePresenterController,
    EventFactory<TView>,
    DoOnlyWhenAttachedStrategy.Factory<TView>,
    DoExactlyOnceStrategy.Factory<TView> {

    /**
     * Create proxy for view state. Proxy must contain all properties defined in TViewState.
     * Properties of proxy must use delegates created by [SummerPresenter.stateIn] method
     *
     * You can use summer plugin to make overriding of this method easier
     *
     * Example:
     *
     * interface FeatureView {
     *     var prop: Int
     * }
     *
     * override val viewProxy = object : FeatureView {
     *     override var prop by store({ ::prop }, initial = 0)
     * }
     */
    abstract val viewProxy: TView

    var viewProvider: ViewProvider<TView>? = null

    private var viewCreatedWasCalled = false

    /**
     * Must be called when view is created. May be called multiple times
     */
    fun viewCreated(view: TView) {
        // restore call placed there because presenter methods may be called due view initialization.
        // restore must be called after initView
        stores.forEach { it.restore() }
        events.forEach { it.viewCreated(view) }

        if (!viewCreatedWasCalled) {
            onEnter()
        }
        viewCreatedWasCalled = true
    }

    override fun viewCreatedUnsafe(view: Any) {
        @Suppress("UNCHECKED_CAST")
        viewCreated(view as TView)
    }

    /**
     * Called when [viewCreated] was called first times
     */
    open fun onEnter() {}

    /**
     * Used for state storing by default. Can be overridden
     */
    open val defaultStore: SummerStore = InMemoryStore()

    private val stores = mutableSetOf<SummerStore>()

    /**
     * Shorthand for [stateIn] with [defaultStore] of this presenter
     */
    protected fun <T> state(
        getMirrorProperty: GetMirrorProperty<TView, T>? = null,
        initial: T
    ) = stateIn(
        getMirrorProperty = getMirrorProperty,
        initial = initial,
        store = defaultStore
    )

    /**
     * Create delegate for property stored in any store
     *
     * May be called in viewProxy or just
     * in presenter if some sort of persistent store is used.
     *
     * If view is not null value will be stored in store
     * and mirrored in mirror property if view exists
     *
     * If view is null value will be stored only in store
     *
     * @return state property delegate
     */
    fun <T> stateIn(
        getMirrorProperty: GetMirrorProperty<TView, T>? = null,
        initial: T,
        store: SummerStore
    ): SummerStore.StateDelegate<T> {
        return store.createState(
            onSet = { value ->
                val currentView = viewProvider?.getView()
                if (currentView != null && getMirrorProperty != null) {
                    val property = getMirrorProperty(currentView)
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
}

typealias GetMirrorProperty<TView, T> = (TView) -> KMutableProperty0<T>

/**
 * Non-generic protocol that can be used to call lifecycle events of [SummerPresenter]
 * in languages without covariant types support (like Swift)
 */
interface UnsafePresenterController {
    /**
     * Same as [SummerPresenter.viewCreated] but with unsafe typecast.
     * Used when view can not pass typed [view]
     */
    fun viewCreatedUnsafe(view: Any)
}