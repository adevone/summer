package summer.state.strategies

import summer.GetViewProvider
import summer.state.*

/**
 * Saves state in [InMemoryStore].
 */
open class InMemoryStrategy<T, TView> : StateProxyStrategy<T, TView, InMemoryStore> {

    override fun getValue(
        viewProperty: ViewProperty<T, TView, InMemoryStore>,
        owner: InMemoryStore,
        getViewProvider: GetViewProvider<TView>,
    ): T {
        val key = viewProperty.proxyProperty.name
        return if (owner.isInit(key)) {
            owner.get(key)
        } else {
            viewProperty.initial
        }
    }

    override fun setValue(
        value: T,
        viewProperty: ViewProperty<T, TView, InMemoryStore>,
        owner: InMemoryStore,
        getViewProvider: GetViewProvider<TView>,
    ) {
        owner.set(key = viewProperty.proxyProperty.name, value)
        val view = getViewProvider.getView()
        if (view != null) {
            viewProperty.setIfExists(value, view)
        }
    }

    override fun viewCreated(
        viewProperty: ViewProperty<T, TView, InMemoryStore>,
        owner: InMemoryStore,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            val key = viewProperty.proxyProperty.name
            if (owner.isInit(key)) {
                val value = owner.get<T>(key)
                viewProperty.setIfExists(value, view)
            } else {
                viewProperty.setIfExists(viewProperty.initial, view)
            }
        }
    }

    interface ProxyFactory<TView> : StateProxyFactory<TView>, InMemoryStore.Provider {

        fun <T> state(
            getViewProperty: GetViewProperty<T, TView>? = null,
            initial: T,
        ): StateProxy.Provider<T, TView, InMemoryStore> {
            return state(
                getViewProperty,
                initial,
                InMemoryStrategy(),
                owner = inMemoryStore,
                listener = null
            )
        }
    }
}