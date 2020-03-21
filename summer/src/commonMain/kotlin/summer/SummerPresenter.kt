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
 * and executing of events (see [SummerEvent]).
 *
 * [TView] is type of associated view.
 * [TStateLower] is lower type of state properties. It useful to implement persistent [SummerStore]
 *               that allows only persistable values.
 */
abstract class SummerPresenter<TView, TStateLower> :
    PresenterController,
    EventFactory<TView>,
    DoOnlyWhenAttachedStrategy.Factory<TView>,
    DoExactlyOnceStrategy.Factory<TView>,
    ViewProvider<TView> {

    /**
     * Create proxy for view state. Proxy must contain all properties defined in TViewState.
     * Properties of proxy must use delegates created by [SummerPresenter.stateIn] method.
     *
     * You can use summer plugin to make overriding of this method easier.
     *
     * Example:
     *
     * interface FeatureView {
     *     var prop: Int
     * }
     *
     * override val viewProxy = object : FeatureView {
     *     override var prop by state({ it::prop }, initial = 0)
     * }
     */
    abstract val viewProxy: TView

    override var getView: () -> TView? = { null }

    override fun setViewProviderUnsafe(unsafeGetView: () -> Any?) {
        getView = {
            val view = unsafeGetView()
            @Suppress("UNCHECKED_CAST")
            view as TView?
        }
    }

    private var viewCreatedWasCalled = false

    /**
     * Must be called when view is created. May be called multiple times.
     */
    override fun viewCreated() {
        // restore call placed there because presenter methods may be called due view initialization.
        // restore must be called after initView
        stores.forEach { it.restore() }
        events.forEach { it.viewCreated() }

        if (!viewCreatedWasCalled) {
            onEnter()
        }
        viewCreatedWasCalled = true
    }

    /**
     * Called when [viewCreated] was called first times.
     */
    open fun onEnter() {}

    /**
     * Used for state storing by default. Can be overridden.
     */
    open val defaultStore: SummerStore<TStateLower> = InMemoryStore()

    private val stores = mutableSetOf<SummerStore<TStateLower>>()

    /**
     * Shorthand for [stateIn] with [defaultStore] of this presenter.
     */
    protected fun <T : TStateLower> state(
        getMirrorProperty: GetMirrorProperty<TView, T>? = null,
        initial: T
    ) = stateIn(
        getMirrorProperty = getMirrorProperty,
        initial = initial,
        store = defaultStore
    )

    /**
     * Create delegate for property stored in any store.
     *
     * May be called in viewProxy or just
     * in presenter if some sort of persistent store is used.
     *
     * If view is not null value will be stored in store
     * and mirrored in mirror property if view exists.
     *
     * If view is null value will be stored only in store.
     *
     * @return state property delegate
     */
    fun <T : TStateLower> stateIn(
        getMirrorProperty: GetMirrorProperty<TView, T>? = null,
        initial: T,
        store: SummerStore<TStateLower>
    ): SummerStore.StateDelegate<T> {
        return store.createState(
            onSet = { value ->
                val currentView = getView()
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

interface ViewProvider<TView> {
    val getView: () -> TView?
}

private typealias GetMirrorProperty<TView, T> = (TView) -> KMutableProperty0<T>

/**
 * Non-generic protocol that can be used to call lifecycle events of [SummerPresenter]
 * in languages without covariant types support (like Swift).
 */
interface PresenterController {
    fun viewCreated()
    fun setViewProviderUnsafe(unsafeGetView: () -> Any?)
}