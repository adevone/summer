package summer

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

class StateHolder {

    private val propertiesToRestore = mutableMapOf<SummerPresenter<*, *, *>, MutableList<PropertyToRestore<*, *>>>()

    private val storeManager = StoreDelegateManager<Any>(propertiesToRestore)

    internal fun <T> store(
        getKey: () -> Any,
        isPresenterDestroyed: () -> Boolean,
        presenter: SummerPresenter<*, *, *>,
        viewStateProperty: KMutableProperty0<T>?,
        initialValue: T
    ): StoredProperty<T> = storeManager.obtain(
        getKey = getKey,
        isPresenterDestroyed = isPresenterDestroyed,
        presenter = presenter,
        viewStateProperty = viewStateProperty,
        initialValue = initialValue
    )

    internal fun isPresent(owner: Any): Boolean {
        return storeManager.isPresent(owner)
    }

    fun onDestroyOwner(owner: Any) {
        storeManager.clear(owner)
    }

    internal fun restoreProperties(presenter: SummerPresenter<*, *, *>) {
        propertiesToRestore[presenter]?.forEach { it.restore() }
        propertiesToRestore.remove(presenter)
    }
}

private class StoreDelegateManager<TKey>(
    private val propertiesToRestore: MutableMap<SummerPresenter<*, *, *>, MutableList<PropertyToRestore<*, *>>>
) {
    private var storedValuesByKey = mutableMapOf<TKey, MutableMap<String, Any?>>()
    private var isInitByKey = mutableMapOf<TKey, MutableMap<String, Unit>>()

    fun <T> obtain(
        getKey: () -> TKey,
        isPresenterDestroyed: () -> Boolean,
        presenter: SummerPresenter<*, *, *>,
        viewStateProperty: KMutableProperty0<T>?,
        initialValue: T
    ): Delegate<T> {
        val delegate = Delegate(
            viewStateProperty = viewStateProperty,
            getKey = getKey,
            isPresenterDestroyed = isPresenterDestroyed,
            initialValue = initialValue
        )
        if (viewStateProperty != null) {
            val key = getKey()
            if (key !in storedValuesByKey) storedValuesByKey[key] = mutableMapOf()
            if (viewStateProperty.name !in storedValuesByKey[key]!!) {
                storedValuesByKey[key]!![viewStateProperty.name] = initialValue
            }
            val value = storedValuesByKey[key]!![viewStateProperty.name] as T
            if (presenter !in propertiesToRestore) {
                propertiesToRestore[presenter] = mutableListOf()
            }
            propertiesToRestore[presenter]!!.add(PropertyToRestore(viewStateProperty, delegate, value))
        }
        return delegate
    }

    inner class Delegate<T>(
        private val viewStateProperty: KMutableProperty0<T>?,
        getKey: () -> TKey,
        private val isPresenterDestroyed: () -> Boolean,
        private val initialValue: T
    ) : StoredProperty<T> {

        private val key by lazy(getKey)

        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            val isInit = isInitByKey[key]?.get(property.name) != null
            return if (isInit) {
                storedValuesByKey[key]?.get(property.name) as T
            } else {
                initialValue
            }
        }

        /**
         * Если какая-то property была установлена из LifecyclePresenter.initView,
         * то
         */
        var wasSet = false

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {

            if (key !in storedValuesByKey) storedValuesByKey[key] = mutableMapOf()
            storedValuesByKey[key]!![property.name] = value

            if (key !in isInitByKey) isInitByKey[key] = mutableMapOf()
            isInitByKey[key]!![property.name] = Unit

            if (!isPresenterDestroyed()) {
                viewStateProperty?.set(value)
            }

            wasSet = true
        }
    }

    fun clear(key: TKey) {
        storedValuesByKey.remove(key)
        isInitByKey.remove(key)
    }

    fun isPresent(key: TKey): Boolean {
        return key in storedValuesByKey
    }
}

private class PropertyToRestore<T, TKey>(
    private val viewStateProperty: KMutableProperty0<T>,
    private val delegate: StoreDelegateManager<TKey>.Delegate<T>,
    private val value: T
) {
    fun restore() {
        if (!delegate.wasSet) {
            viewStateProperty.set(value)
        }
    }

    override fun toString() = "${viewStateProperty.name}=$value"
}

interface StoredProperty<T> : ReadWriteProperty<Any, T>