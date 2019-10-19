package summer.store

import kotlin.reflect.KMutableProperty0

class SummerStoresSubscriber {

    private val observerPropertyHolders = mutableListOf<ObserverPropertyHolder<*>>()
    private val stores = mutableSetOf<SummerStore>()
    fun <T> storeIn(
        mirrorProperty: KMutableProperty0<T>? = null,
        initialValue: T,
        store: SummerStore
    ): SummerStore.DelegateProvider<T> {
        val propertyHolder = ObserverPropertyHolder(mirrorProperty)
        observerPropertyHolders.add(propertyHolder)
        return store.store(
            onSet = { value ->
                propertyHolder.setIfNotEmpty(value)
            },
            initialValue = initialValue
        ).also {
            stores.add(store)
        }
    }

    fun onObserverConnect() {
        stores.forEach { it.restore() }
    }

    fun onObserverDisconnect() {
        this.observerPropertyHolders.forEach { it.destroy() }
        this.observerPropertyHolders.clear()
    }
}

private class ObserverPropertyHolder<T>(
    private var property: KMutableProperty0<T>?
) {
    fun setIfNotEmpty(value: T) {
        property?.set(value)
    }

    fun destroy() {
        property = null
    }
}