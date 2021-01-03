package summer.state

import summer.SummerViewModel
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
            initial: T
        ): StateProxy.Provider<T, TView, InMemoryStoreProvider> {
            return state(getViewProperty, initial, InMemoryStrategy())
        }
    }
}

/**
 * Owner of [InMemoryStrategy]
 */
interface InMemoryStoreProvider {
    val inMemoryStore: InMemoryStore
}

/**
 * Default store for [SummerViewModel].
 * Saved values will be restored only when they saved in same instance.
 */
class InMemoryStore {
    private var isInitByKey = mutableMapOf<String, Unit>()
    private val storedValuesByKey = mutableMapOf<String, Any?>()

    fun isInit(key: String): Boolean {
        return key in isInitByKey
    }

    fun <T> get(key: String): T {
        @Suppress("UNCHECKED_CAST")
        return storedValuesByKey[key] as T
    }

    fun <T> set(key: String, value: T) {
        storedValuesByKey[key] = value
        isInitByKey[key] = Unit
    }
}