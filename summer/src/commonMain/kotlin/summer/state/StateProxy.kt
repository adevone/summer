package summer.state

import summer.GetViewProvider
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

class StateProxy<T, out TView, in TOwner>(
    proxyProperty: KProperty<*>,
    getViewProperty: GetViewProperty<T, TView>?,
    initial: T,
    private val getViewProvider: GetViewProvider<TView>,
    private val owner: TOwner,
    private val listener: StateProxyListener<TView, TOwner>?,
    private val strategy: StateProxyStrategy<T, TView, TOwner>,
) : ReadWriteProperty<Any?, T> {

    private val viewProperty = ViewProperty(
        initial,
        proxyProperty,
        getViewProperty,
        owner,
        listener,
        strategy
    )

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return strategy.getValue(viewProperty, owner, getViewProvider)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        strategy.setValue(value, viewProperty, owner, getViewProvider)
        listener?.proxySet(value, property, owner, strategy)
    }

    fun viewCreated() {
        strategy.viewCreated(viewProperty, owner, getViewProvider)
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
 * Provider of view property to mirror store state
 */
typealias GetViewProperty<T, TView> = (TView) -> KMutableProperty0<T>