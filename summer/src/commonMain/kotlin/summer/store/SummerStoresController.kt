package summer.store

import kotlin.reflect.KMutableProperty0

class SummerStoresController {

    private val mirrorPropertyHolders = mutableListOf<MirrorPropertyHolder<*>>()
    private val stores = mutableSetOf<SummerStore>()
    fun <T> storeIn(
        mirrorProperty: KMutableProperty0<T>? = null,
        initialValue: T,
        store: SummerStore
    ): SummerStore.DelegateProvider<T> {
        val propertyHolder = MirrorPropertyHolder(mirrorProperty)
        mirrorPropertyHolders.add(propertyHolder)
        return store.store(
            onSet = { value ->
                propertyHolder.setIfNotEmpty(value)
            },
            initialValue = initialValue
        ).also {
            stores.add(store)
        }
    }

    fun onMirrorConnect() {
        stores.forEach { it.restore() }
    }

    fun onMirrorDisconnect() {
        this.mirrorPropertyHolders.forEach { it.destroy() }
        this.mirrorPropertyHolders.clear()
    }
}

private class MirrorPropertyHolder<T>(
    private var property: KMutableProperty0<T>?
) {
    fun setIfNotEmpty(value: T) {
        property?.set(value)
    }

    fun destroy() {
        property = null
    }
}