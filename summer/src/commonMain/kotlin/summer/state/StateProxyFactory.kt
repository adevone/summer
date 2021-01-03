package summer.state

import summer.ProxyFactory

/**
 * DSL to create [StateProxy.Provider].
 *
 * [TView] see [StateProxyStrategy]
 * [TOwner] see [StateProxyStrategy]
 */
interface StateProxyFactory<TView, TOwner> : ProxyFactory<TView> {
    /**
     * Creates provider of [StateProxy].
     *
     * May be called in viewProxy or just
     * in viewModel if some sort of persistent store is used
     * and you want to store viewModel state too.
     *
     * If view is not null than value will be saved in store
     * and mirrored in mirror property if view exists.
     *
     * If view is null than value will be only saved in store.
     */
    fun <T> state(
        getViewProperty: GetViewProperty<T, TView>? = null,
        initial: T,
        strategy: StateProxyStrategy<T, TOwner>
    ): StateProxy.Provider<T, TView, TOwner> {
        return StateProxy.Provider(
            getViewProperty,
            initial,
            getViewProvider(),
            owner = stateProxyOwner(),
            listener = stateListener(),
            strategy,
            proxyCreated = { delegate ->
                stateProxyCreated(delegate)
            }
        )
    }

    fun stateProxyOwner(): TOwner

    fun stateProxyCreated(proxy: StateProxy<*, *, *>)

    fun stateListener(): StateProxyListener<TView, TOwner>? = null
}