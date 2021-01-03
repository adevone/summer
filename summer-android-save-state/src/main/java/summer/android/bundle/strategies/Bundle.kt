package summer.android.bundle.strategies

import android.os.Bundle
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateDelegateProvider
import summer.state.GetViewProperty
import summer.state.StateProxyFactory

object BundleBundleStateStrategy : BundleStateStrategy<Bundle?>(
    getValue = Bundle::getBundle,
    setValue = Bundle::putBundle
) {
    interface Factory<TView> : StateProxyFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetViewProperty<Bundle?, TView>? = null,
            initial: Bundle?,
        ): BundleStateDelegateProvider<Bundle?, TView> {
            return state(getMirrorProperty, initial, BundleBundleStateStrategy)
        }
    }
}