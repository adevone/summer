package summer.strategy

import kotlinx.serialization.KSerializer
import summer.ViewStateProvider
import summer.state.*
import kotlin.reflect.KProperty

class SerializationStrategy<T, TView>(
    private val serializer: KSerializer<T>,
) : StateProxyStrategy<T, TView, SerializationStore> {

    override fun getValue(
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: SerializationStore,
        viewStateProvider: ViewStateProvider<TView>
    ): T {
        val key = proxyProperty.name
        return if (owner.isInit(key)) {
            owner.get(key)
        } else {
            initial
        }
    }

    override fun setValue(
        value: T,
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: SerializationStore,
        viewStateProvider: ViewStateProvider<TView>
    ) {
        owner.set(key = proxyProperty.name, value, serializer)
        val view = viewStateProvider.getView()
        if (view != null) {
            viewPropertySetter.setIfExists(value, view)
        }
    }

    override fun viewCreated(
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: SerializationStore,
        viewStateProvider: ViewStateProvider<TView>
    ) {
        val view = viewStateProvider.getView()
        if (view != null) {
            val key = proxyProperty.name
            if (owner.isInit(key)) {
                val value = owner.get<T>(key)
                viewPropertySetter.setIfExists(value, view)
            } else {
                viewPropertySetter.setIfExists(initial, view)
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