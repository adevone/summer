package summer.strategy

import kotlinx.serialization.KSerializer
import summer.GetViewProvider
import summer.state.*

class SerializationStrategy<T, TView>(
    private val serializer: KSerializer<T>,
) : StateProxyStrategy<T, TView, SerializationStore> {

    override fun getValue(
        viewProperty: ViewProperty<T, TView, SerializationStore>,
        owner: SerializationStore,
        getViewProvider: GetViewProvider<TView>,
    ): T {
        val key = viewProperty.proxyProperty.name
        return if (owner.isInit(key)) {
            owner.get(key)
        } else {
            viewProperty.initial
        }
    }

    override fun setValue(
        value: T,
        viewProperty: ViewProperty<T, TView, SerializationStore>,
        owner: SerializationStore,
        getViewProvider: GetViewProvider<TView>,
    ) {
        owner.set(key = viewProperty.proxyProperty.name, value, serializer)
        val view = getViewProvider.getView()
        if (view != null) {
            viewProperty.setIfExists(value, view)
        }
    }

    override fun viewCreated(
        viewProperty: ViewProperty<T, TView, SerializationStore>,
        owner: SerializationStore,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            val key = viewProperty.proxyProperty.name
            if (owner.isInit(key)) {
                val value = owner.get<T>(key)
                viewProperty.setIfExists(value, view)
            } else {
                viewProperty.setIfExists(viewProperty.initial, view)
            }
        }
    }

    interface Factory<TView> : StateProxyFactory<TView>, SerializationStore.Holder {

        fun <T> state(
            getViewProperty: GetViewProperty<T, TView>? = null,
            initial: T,
            serializer: KSerializer<T>,
        ): StateProxy.Provider<T, TView, SerializationStore> {
            return state(
                getViewProperty,
                initial,
                SerializationStrategy(serializer),
                owner = serializationStore,
                listener = null
            )
        }
    }
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

    interface Holder {
        var serializationStore: SerializationStore
    }
}

data class ValueWithSerializer<T>(
    val value: T,
    val serializer: KSerializer<T>,
)