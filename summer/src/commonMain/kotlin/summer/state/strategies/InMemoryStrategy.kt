package summer.state.strategies

import summer.state.GetViewProperty
import summer.state.StateProxy
import summer.state.StateProxyFactory
import summer.state.StateProxyStrategy
import kotlin.reflect.KProperty

/**
 * Saves state only in memory.
 */
class InMemoryStrategy<T> : StateProxyStrategy<T, InMemoryStoreProvider> {

    override fun get(owner: InMemoryStoreProvider, prop: KProperty<*>): T {
        return owner.inMemoryStore.get(prop.name)
    }

    override fun set(owner: InMemoryStoreProvider, prop: KProperty<*>, value: T) {
        owner.inMemoryStore.set(prop.name, value)
    }

    override fun wasStored(owner: InMemoryStoreProvider, prop: KProperty<*>): Boolean {
        return owner.inMemoryStore.isInit(prop.name)
    }

    interface ProxyFactory<TView> : StateProxyFactory<TView, InMemoryStoreProvider> {

        fun <T> state(
            getViewProperty: GetViewProperty<T, TView>? = null,
            initial: T,
        ): StateProxy.Provider<T, TView, InMemoryStoreProvider> {
            return state(getViewProperty, initial, InMemoryStrategy())
        }
    }
}