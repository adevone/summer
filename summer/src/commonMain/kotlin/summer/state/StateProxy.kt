package summer.state

import summer.GetViewProvider
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

class StateProxy<T, TView, TOwner>(
    private val property: KProperty<*>,
    private val getViewProperty: GetViewProperty<T, TView>?,
    private val initial: T,
    private val getViewProvider: GetViewProvider<TView>,
    private val owner: TOwner,
    private val listener: StateProxyListener<TView, TOwner>?,
    private val strategy: StateProxyStrategy<T, TOwner>,
) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val isInit = strategy.wasStored(owner, property)
        return if (isInit) {
            strategy.get(owner, property)
        } else {
            initial
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        strategy.set(owner, property, value)
        listener?.proxySet(value, property, owner, strategy)
        setViewPropertyIfViewExists(value)
    }

    fun restore() {
        val isInit = strategy.wasStored(owner, property)
        if (isInit) {
            val value = strategy.get(owner, property)
            setViewPropertyIfViewExists(value)
        } else {
            setViewPropertyIfViewExists(value = initial)
        }
    }

    private fun setViewPropertyIfViewExists(value: T) {
        val view = getViewProvider.getView()
        if (view != null) {
            getViewProperty?.let { getViewProperty ->
                val viewProperty = getViewProperty(view)
                viewProperty.set(value)
                listener?.viewPropertySet(value, property, view, owner, strategy)
            }
        }
    }

    class Provider<T, TView, TOwner>(
        private val getViewProperty: GetViewProperty<T, TView>?,
        private val initial: T,
        private val getViewProvider: GetViewProvider<TView>,
        private val owner: TOwner,
        private val listener: StateProxyListener<TView, TOwner>?,
        private val strategy: StateProxyStrategy<T, TOwner>,
        private val proxyCreated: (StateProxy<T, TView, TOwner>) -> Unit,
    ) : PropertyDelegateProvider<Any?, StateProxy<T, TView, TOwner>> {

        override fun provideDelegate(
            thisRef: Any?,
            property: KProperty<*>,
        ): StateProxy<T, TView, TOwner> {
            val proxy = StateProxy(
                property,
                getViewProperty,
                initial,
                getViewProvider,
                owner,
                listener,
                strategy
            )
            proxyCreated(proxy)
            return proxy
        }
    }
}

/**
 * Provider of view property to mirror store state
 */
typealias GetViewProperty<T, TView> = (TView) -> KMutableProperty0<T>