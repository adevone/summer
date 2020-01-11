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

class SummerStoresController<TViewState, TViewMethods> {

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
                val currentViewState = viewState
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

typealias GetMirrorProperty<TViewState, T> = (TViewState) -> KMutableProperty0<T>