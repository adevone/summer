package summer.android.bundle.strategies

import android.os.Bundle
import summer.GetViewProvider
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateProxyProvider
import summer.state.GetViewProperty
import summer.state.StateProxyFactory
import summer.state.StateProxyStrategy
import summer.state.ViewPropertySetter
import kotlin.reflect.KProperty

abstract class BundleStateStrategy<T, TView>(
    private val getValue: Bundle.(String?) -> T,
    private val setValue: Bundle.(String?, T) -> Unit,
) : StateProxyStrategy<T, TView, BundleProvider> {

    override fun getValue(
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: BundleProvider,
        getViewProvider: GetViewProvider<TView>
    ): T {
        val key = proxyProperty.name
        return if (owner.bundle.containsKey(key)) {
            owner.bundle.getValue(key)
        } else {
            initial
        }
    }

    override fun setValue(
        value: T,
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: BundleProvider,
        getViewProvider: GetViewProvider<TView>
    ) {
        owner.bundle.setValue(proxyProperty.name, value)
        val view = getViewProvider.getView()
        if (view != null) {
            viewPropertySetter.setIfExists(value, view)
        }
    }

    override fun viewCreated(
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: BundleProvider,
        getViewProvider: GetViewProvider<TView>
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            val key = proxyProperty.name
            if (owner.bundle.containsKey(key)) {
                val value = owner.bundle.getValue(key)
                viewPropertySetter.setIfExists(value, view)
            } else {
                viewPropertySetter.setIfExists(initial, view)
            }
        }
    }

    interface BaseProxyFactory<TView> : StateProxyFactory<TView>, BundleProvider {

        fun <T> state(
            getMirrorProperty: GetViewProperty<T, TView>? = null,
            initial: T,
            strategy: BundleStateStrategy<T, TView>,
        ): BundleStateProxyProvider<T, TView> {
            return state(
                getMirrorProperty,
                initial,
                strategy,
                owner = this
            )
        }
    }
}