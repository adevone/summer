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

    private val propertiesToRestore = mutableListOf<PropertyToRestore<*>>()
    private var storedValuesByKey = mutableMapOf<String, Any?>()
    private var isInitByKey = mutableMapOf<String, Unit>()

    override fun <T> store(onSet: (T) -> Unit, initial: T): SummerStore.DelegateProvider<T> {
        return InMemoryDelegateProvider(onSet, initial)
    }

    override fun restore() {
        propertiesToRestore.forEach { it.restore() }
    }

    private inner class InMemoryDelegateProvider<T>(
        private val onSet: (T) -> Unit,
        private val initial: T
    ) : SummerStore.DelegateProvider<T> {

        override fun provideDelegate(
            thisRef: Any?,
            prop: KProperty<*>
        ): ReadWriteProperty<Any?, T> {
            val delegate = Delegate(
                onSet = onSet,
                initial = initial
            )
            if (prop.name !in storedValuesByKey) {
                storedValuesByKey[prop.name] = initial
            }
            @Suppress("UNCHECKED_CAST")
            val value = storedValuesByKey[prop.name] as T
            propertiesToRestore.add(PropertyToRestore(prop.name, onSet, delegate, value))
            return Delegate(
                onSet = onSet,
                initial = initial
            )
        }
    }

    private inner class Delegate<T>(
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

        /**
         * If some property was set before [SummerPresenter.created] it must not be restored
         */
        var wasSet = false

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            storedValuesByKey[property.name] = value
            isInitByKey[property.name] = Unit
            onSet(value)
            wasSet = true
        }
    }

    private class PropertyToRestore<T>(
        private val propName: String,
        private val onSet: (T) -> Unit,
        private val delegate: Delegate<T>,
        private val value: T
    ) {
        fun restore() {
            if (!delegate.wasSet) {
                onSet(value)
            }
        }

        override fun toString() = "$propName=$value"
    }
}