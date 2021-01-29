package summer.android.bundle.strategies

import android.os.Bundle
import summer.android.bundle.BundleStateProxyProvider
import summer.state.GetViewProperty

class BundleBundleStateStrategy<TView> : BundleStateStrategy<Bundle?, TView>(
    getValue = Bundle::getBundle,
    setValue = Bundle::putBundle
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Bundle?, TView>? = null,
            initial: Bundle?,
        ): BundleStateProxyProvider<Bundle?, TView> {
            return state(getMirrorProperty, initial, BundleBundleStateStrategy())
        }
    }
}