package summer.state

import summer.SummerPresenter
import kotlin.reflect.KProperty

class InMemoryStateStrategy<T> : SummerStateStrategy<T, InMemoryStoreProvider> {

    override fun get(owner: InMemoryStoreProvider, prop: KProperty<*>): T {
        return owner.inMemoryStore.get(prop.name)
    }

    override fun set(owner: InMemoryStoreProvider, prop: KProperty<*>, value: T) {
        owner.inMemoryStore.set(prop.name, value)
    }

    override fun wasStored(owner: InMemoryStoreProvider, prop: KProperty<*>): Boolean {
        return owner.inMemoryStore.isInit(prop.name)
    }

    interface Factory<TView> : StateFactory<TView, InMemoryStoreProvider> {

        fun <T> state(
            getMirrorProperty: GetMirrorProperty<TView, T>? = null,
            initial: T
        ): StateDelegate.Provider<T, InMemoryStoreProvider> {
            return state(getMirrorProperty, initial, InMemoryStateStrategy())
        }
    }
}

interface InMemoryStoreProvider {
    val inMemoryStore: InMemoryStore
}

/**
 * Default store that can be passed to [SummerPresenter.state]
 * Stores values in memory and doesn't restores them after app relaunch
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