package summer.android.bundle.strategies

import android.os.Bundle
import summer.GetViewProvider
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateProxyProvider
import summer.state.GetViewProperty
import summer.state.StateProxyFactory
import summer.state.StateProxyStrategy
import summer.state.ViewProperty

abstract class BundleStateStrategy<T, TView>(
    private val getValue: Bundle.(String?) -> T,
    private val setValue: Bundle.(String?, T) -> Unit,
) : StateProxyStrategy<T, TView, BundleProvider> {

    override fun getValue(
        viewProperty: ViewProperty<T, TView, BundleProvider>,
        owner: BundleProvider,
        getViewProvider: GetViewProvider<TView>,
    ): T {
        val key = viewProperty.proxyProperty.name
        return if (owner.bundle.containsKey(key)) {
            owner.bundle.getValue(key)
        } else {
            viewProperty.initial
        }
    }

    override fun setValue(
        value: T,
        viewProperty: ViewProperty<T, TView, BundleProvider>,
        owner: BundleProvider,
        getViewProvider: GetViewProvider<TView>,
    ) {
        owner.bundle.setValue(viewProperty.proxyProperty.name, value)
        val view = getViewProvider.getView()
        if (view != null) {
            viewProperty.setIfExists(value, view)
        }
    }

    override fun viewCreated(
        viewProperty: ViewProperty<T, TView, BundleProvider>,
        owner: BundleProvider,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            val key = viewProperty.proxyProperty.name
            if (owner.bundle.containsKey(key)) {
                val value = owner.bundle.getValue(key)
                viewProperty.setIfExists(value, view)
            } else {
                viewProperty.setIfExists(viewProperty.initial, view)
            }
        }
    }

    interface BaseProxyFactory<TView> : StateProxyFactory<TView>, BundleProvider {

        fun <T> state(
            getMirrorProperty: GetViewProperty<T, TView>? = null,
            initial: T,
            strategy: StateProxyStrategy<T, TView, BundleProvider>,
        ): BundleStateProxyProvider<T, TView> {
            return state(
                getMirrorProperty,
                initial,
                strategy,
                owner = this,
                listener = null
            )
        }
    }
}