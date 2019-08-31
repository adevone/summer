package summer

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface SummerStore {
    fun <T> store(onSet: (T) -> Unit, initialValue: T): DelegateProvider<T>
    fun restore()

    interface DelegateProvider<T> {
        operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): ReadWriteProperty<Any?, T>
    }
}

class InMemoryStore : SummerStore {

    private val propertiesToRestore = mutableListOf<PropertyToRestore<*>>()
    private var storedValuesByKey = mutableMapOf<String, Any?>()
    private var isInitByKey = mutableMapOf<String, Unit>()

    override fun <T> store(onSet: (T) -> Unit, initialValue: T): SummerStore.DelegateProvider<T> {
        return InMemoryDelegateProvider(onSet, initialValue)
    }

    override fun restore() {
        propertiesToRestore.forEach { it.restore() }
    }

    private inner class InMemoryDelegateProvider<T>(
        private val onSet: (T) -> Unit,
        private val initialValue: T
    ) : SummerStore.DelegateProvider<T> {

        override fun provideDelegate(
            thisRef: Any?,
            prop: KProperty<*>
        ): ReadWriteProperty<Any?, T> {
            val delegate = Delegate(
                onSet = onSet,
                initialValue = initialValue
            )
            if (prop.name !in storedValuesByKey) {
                storedValuesByKey[prop.name] = initialValue
            }
            @Suppress("UNCHECKED_CAST")
            val value = storedValuesByKey[prop.name] as T
            propertiesToRestore.add(PropertyToRestore(prop.name, onSet, delegate, value))
            return Delegate(
                onSet = onSet,
                initialValue = initialValue
            )
        }
    }

    private inner class Delegate<T>(
        private val onSet: (T) -> Unit,
        private val initialValue: T
    ) : ReadWriteProperty<Any?, T> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            val isInit = isInitByKey[property.name] != null
            return if (isInit) {
                @Suppress("UNCHECKED_CAST")
                storedValuesByKey[property.name] as T
            } else {
                initialValue
            }
        }

        /**
         * Если какая-то property была установлена из SummerPresenter.initView,
         * то её не нужно восстанавливать
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