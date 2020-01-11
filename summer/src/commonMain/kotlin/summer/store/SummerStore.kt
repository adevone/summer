package summer.store

import summer.SummerPresenter
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Store that can be used for view state restoring
 */
interface SummerStore {

    /**
     * Provides delegate that will store passed values in this store
     */
    fun <T> store(onSet: (T) -> Unit, initial: T): DelegateProvider<T>

    /**
     * Must call [store].onSet for each property that was set
     */
    fun restore()

    /**
     * Interface for delegate provided by [store]
     */
    interface DelegateProvider<T> {
        operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): ReadWriteProperty<Any?, T>
    }
}

/**
 * Default store that can be passed to [SummerPresenter.storeIn] or [SummerPresenter.localStore]
 * Stores values in memory and doesn't restores them after app relaunch
 */
class InMemoryStore : SummerStore {

    private val delegates = mutableListOf<Delegate<*>>()
    private var storedValuesByKey = mutableMapOf<String, Any?>()
    private var isInitByKey = mutableMapOf<String, Unit>()

    override fun <T> store(onSet: (T) -> Unit, initial: T): SummerStore.DelegateProvider<T> {
        return InMemoryDelegateProvider(onSet, initial)
    }

    override fun restore() {
        delegates.forEach { it.restore() }
    }

    private inner class InMemoryDelegateProvider<T>(
        private val onSet: (T) -> Unit,
        private val initial: T
    ) : SummerStore.DelegateProvider<T> {

        override fun provideDelegate(
            thisRef: Any?,
            prop: KProperty<*>
        ): ReadWriteProperty<Any?, T> {
            if (prop.name !in storedValuesByKey) {
                storedValuesByKey[prop.name] = initial
            }
            val delegate = Delegate(
                propName = prop.name,
                onSet = onSet,
                initial = initial
            )
            delegates.add(delegate)
            return delegate
        }
    }

    private inner class Delegate<T>(
        private val propName: String,
        private val onSet: (T) -> Unit,
        private val initial: T
    ) : ReadWriteProperty<Any?, T> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            val isInit = isInitByKey[property.name] != null
            return if (isInit) {
                @Suppress("UNCHECKED_CAST")
                storedValuesByKey[property.name] as T
            } else {
                initial
            }
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            storedValuesByKey[property.name] = value
            isInitByKey[property.name] = Unit
            onSet(value)
        }

        fun restore() {
            @Suppress("UNCHECKED_CAST")
            val value = storedValuesByKey[propName] as T
            onSet(value)
        }
    }

    override fun toString() = "propertiesToRestore=${delegates.joinToString()}, " +
            "storedValuesByKey=${storedValuesByKey.entries.joinToString { (key, value) -> "$key=$value" }}, " +
            "isInitByKey=${isInitByKey.entries.joinToString { (key, isInit) -> "$key isInit=$isInit" }}"
}