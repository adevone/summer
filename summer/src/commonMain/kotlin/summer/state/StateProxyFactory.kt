package summer.state

import summer.ProxyFactory

/**
 * DSL to create [StateProxy.Provider].
 *
 * [TView] see [StateProxyStrategy]
 */
interface StateProxyFactory<TView> : ProxyFactory<TView> {
    /**
     * Creates provider of [StateProxy].
     *
     * May be called in viewProxy or just
     * in viewModel if some sort of persistent store is used
     * and you want to store viewModel state too.
     *
     * If view is not null than value will be saved in store
     * and mirrored in view property if view exists.
     *
     * If view is null than value will be only saved in store.
     *
     * [TOwner] see [StateProxyStrategy]
     */
    fun <T, TOwner> state(
        getViewProperty: GetViewProperty<T, TView>? = null,
        initial: T,
        strategy: StateProxyStrategy<T, TView, TOwner>,
        owner: TOwner,
        listener: StateProxyListener<TView, TOwner>?,
    ): StateProxy.Provider<T, TView, TOwner> {
        return StateProxy.Provider(
            getViewProperty,
            initial,
            getViewProvider(),
            owner,
            listener,
            strategy,
            proxyCreated = { delegate ->
                stateProxyCreated(delegate)
            }
        )
    }

    fun stateProxyCreated(proxy: StateProxy<*, *, *>)
}