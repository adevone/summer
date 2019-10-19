package summer.view

import summer.store.SummerStoresSubscriber

interface SummerViewStateProxyProvider<TViewState> {

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
    fun createViewStateProxy(vs: TViewState): TViewState
}

class SummerViewController<TViewState, TViewMethods>(
    /**
     * [viewCreated].viewState is observer for SummerStoresSubscriber
     * SummerViewController controls viewState and then calls
     * [SummerStoresSubscriber.onObserverConnect] and [SummerStoresSubscriber.onObserverDisconnect]
     */
    private val storesSubscriber: SummerStoresSubscriber
) {
    var viewStateProxy: TViewState? = null
    var viewMethods: TViewMethods? = null

    /**
     * Must be called when view is created. May be called multiple times
     */
    fun viewCreated(
        viewState: TViewState,
        viewMethods: TViewMethods,
        viewStateProxyProvider: SummerViewStateProxyProvider<TViewState>
    ) {
        this.viewStateProxy = viewStateProxyProvider.createViewStateProxy(viewState)
        this.viewMethods = viewMethods

        // onObserverConnect call placed there because presenter methods may be called in initView.
        // onObserverConnect must be called after initView
        storesSubscriber.onObserverConnect()
    }

    /**
     * Must be called before view destroying. May be called multiple times
     */
    fun viewDestroyed() {
        storesSubscriber.onObserverDisconnect()
        this.viewMethods = null
    }
}