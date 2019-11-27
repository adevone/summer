package summer.store

import kotlin.reflect.KMutableProperty0

interface SummerViewStateProxyProvider<TViewState> {

    /**
     * Create proxy for view state. Proxy must contain all properties defined in TViewState.
     * Properties of proxy must use delegates created by [SummerStoresController.storeIn] method
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
     * override val viewStateProxy = object : FeatureView.State {
     *     override var prop by store({ ::prop }, initial = 0)
     * }
     */
    val viewStateProxy: TViewState
}

class SummerStoresController<TViewState, TViewMethods> : ViewStateShorthandProvider<TViewState> {

    var viewState: TViewState? = null
    var viewMethods: TViewMethods? = null

    private val stores = mutableSetOf<SummerStore>()
    fun <T> storeIn(
        getMirrorProperty: GetMirrorProperty<TViewState, T>? = null,
        initial: T,
        store: SummerStore
    ): SummerStore.DelegateProvider<T> {
        return store.store(
            onSet = { value ->
                if (viewState != null && getMirrorProperty != null) {
                    val property = this.getMirrorProperty()
                    property.set(value)
                }
            },
            initial = initial
        ).also {
            stores.add(store)
        }
    }

    override val vs: TViewState
        get() = viewState ?: throw AssertionError("viewState nullability is not checked")

    /**
     * Must be called when view is created. May be called multiple times
     */
    fun viewCreated(
        viewState: TViewState,
        viewMethods: TViewMethods
    ) {
        this.viewState = viewState
        this.viewMethods = viewMethods

        // onObserverConnect call placed there because presenter methods may be called in initView.
        // onObserverConnect must be called after initView
        stores.forEach { it.restore() }
    }

    /**
     * Must be called before view destroying. May be called multiple times
     */
    fun viewDestroyed() {
        this.viewState = null
        this.viewMethods = null
    }
}

typealias GetMirrorProperty<TViewState, T> = ViewStateShorthandProvider<TViewState>.() -> KMutableProperty0<T>

interface ViewStateShorthandProvider<TViewState> {
    val vs: TViewState
}