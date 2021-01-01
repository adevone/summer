package summer.strategy

import kotlinx.serialization.KSerializer
import summer.state.GetViewProperty
import summer.state.StateFactory
import summer.state.SummerStateDelegate
import summer.state.SummerStateStrategy
import kotlin.reflect.KProperty

class SerializationStrategy<T>(
    private val serializer: KSerializer<T>
) : SummerStateStrategy<T, SerializationStateProvider> {

    override fun get(owner: SerializationStateProvider, prop: KProperty<*>): T {
        return owner.serializationStore.get(prop.name)
    }

    override fun set(owner: SerializationStateProvider, prop: KProperty<*>, value: T) {
        owner.serializationStore.set(prop.name, value, serializer)
    }

    override fun wasStored(owner: SerializationStateProvider, prop: KProperty<*>): Boolean {
        return owner.serializationStore.isInit(prop.name)
    }

    interface Factory<TView> : StateFactory<TView, SerializationStateProvider> {

        fun <T> state(
            getViewProperty: GetViewProperty<T, TView>? = null,
            initial: T,
            serializer: KSerializer<T>
        ): SummerStateDelegate.Provider<T, TView, SerializationStateProvider> {
            return state(getViewProperty, initial, SerializationStrategy(serializer))
        }
    }
}

interface SerializationStateProvider {
    var serializationStore: SerializationStore
}

class SerializationStore {
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