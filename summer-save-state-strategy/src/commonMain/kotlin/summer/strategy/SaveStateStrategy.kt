package summer.strategy

import kotlinx.serialization.KSerializer
import summer.state.GetMirrorProperty
import summer.state.StateFactory
import summer.state.SummerStateDelegate
import summer.state.SummerStateStrategy
import kotlin.reflect.KProperty

class SaveStateStrategy<T>(
    private val serializer: KSerializer<T>
) : SummerStateStrategy<T, SaveStateStoreProvider> {

    override fun get(owner: SaveStateStoreProvider, prop: KProperty<*>): T {
        return owner.saveStateStore.get(prop.name)
    }

    override fun set(owner: SaveStateStoreProvider, prop: KProperty<*>, value: T) {
        owner.saveStateStore.set(prop.name, value, serializer)
    }

    override fun wasStored(owner: SaveStateStoreProvider, prop: KProperty<*>): Boolean {
        return owner.saveStateStore.isInit(prop.name)
    }

    interface Factory<TView> : StateFactory<TView, SaveStateStoreProvider> {

        fun <T> state(
            getMirrorProperty: GetMirrorProperty<TView, T>? = null,
            initial: T,
            serializer: KSerializer<T>
        ): SummerStateDelegate.Provider<T, SaveStateStoreProvider> {
            return state(getMirrorProperty, initial, SaveStateStrategy(serializer))
        }
    }
}

interface SaveStateStoreProvider {
    var saveStateStore: SaveStateStore
}

class SaveStateStore {
    private var isInitByKey = mutableMapOf<String, Unit>()
    private val storedValuesWithSerializerByKey = mutableMapOf<String, ValueWithSerializer<Any?>>()

    fun isInit(key: String): Boolean {
        return key in isInitByKey
    }

    fun <T> get(key: String): T {
        @Suppress("UNCHECKED_CAST")
        return (storedValuesWithSerializerByKey[key] as ValueWithSerializer<T>).value
    }

    fun <T> set(key: String, value: T, serializer: KSerializer<T>) {
        @Suppress("UNCHECKED_CAST")
        storedValuesWithSerializerByKey[key] = ValueWithSerializer(value, serializer) as ValueWithSerializer<Any?>
        isInitByKey[key] = Unit
    }

    fun dump(): Map<String, ValueWithSerializer<Any?>> {
        return storedValuesWithSerializerByKey
    }
}

data class ValueWithSerializer<T>(
    val value: T,
    val serializer: KSerializer<T>
)