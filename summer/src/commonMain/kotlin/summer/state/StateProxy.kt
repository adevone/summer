package summer.state

import summer.GetViewProvider
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

class StateProxy<T, out TView, in TOwner>(
    private val proxyProperty: KProperty<*>,
    getViewProperty: GetViewProperty<T, TView>?,
    private val initial: T,
    private val getViewProvider: GetViewProvider<TView>,
    private val owner: TOwner,
    private val listener: StateProxyListener<TView, TOwner>?,
    private val strategy: StateProxyStrategy<T, TView, TOwner>,
) : ReadWriteProperty<Any?, T> {

    private val viewProperty = ViewPropertySetter(
        getViewProperty,
        onViewPropertySet = { value, view, viewProperty ->
            listener?.viewPropertySet(value, initial, proxyProperty, viewProperty, view, owner, strategy)
        }
    )

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return strategy.getValue(initial, viewProperty, proxyProperty, owner, getViewProvider)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        strategy.setValue(value, initial, viewProperty, proxyProperty, owner, getViewProvider)
        listener?.proxySet(value, initial, property, owner, strategy)
    }

    fun viewCreated() {
        strategy.viewCreated(initial, viewProperty, proxyProperty, owner, getViewProvider)
    }

    class Provider<T, TView, TOwner>(
        private val getViewProperty: GetViewProperty<T, TView>?,
        private val initial: T,
        private val getViewProvider: GetViewProvider<TView>,
        private val owner: TOwner,
        private val listener: StateProxyListener<TView, TOwner>?,
        private val strategy: StateProxyStrategy<T, TView, TOwner>,
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
 * Call this on a real view to get and modify it property.
 */
typealias GetViewProperty<T, TView> = (TView) -> KMutableProperty0<T>