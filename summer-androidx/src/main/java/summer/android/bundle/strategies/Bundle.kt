package summer.android.bundle.strategies

import android.os.Bundle
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateDelegateProvider
import summer.state.GetMirrorProperty
import summer.state.StateFactory

object BundleBundleStateStrategy : BundleStateStrategy<Bundle?>(
    getValue = Bundle::getBundle,
    setValue = Bundle::putBundle
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Bundle?>? = null,
            initial: Bundle?
        ): BundleStateDelegateProvider<Bundle?> {
            return state(getMirrorProperty, initial, BundleBundleStateStrategy)
        }
    }
}