package summer.android.bundle.strategies

import android.os.Bundle
import android.os.IBinder
import android.util.Size
import android.util.SizeF
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateDelegateProvider
import summer.state.GetViewProperty
import summer.state.StateFactory
import java.io.Serializable

object BinderBundleStateStrategy : BundleStateStrategy<IBinder?>(
    getValue = Bundle::getBinder,
    setValue = Bundle::putBinder
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetViewProperty<IBinder?, TView>? = null,
            initial: IBinder?
        ): BundleStateDelegateProvider<IBinder?, TView> {
            return state(getMirrorProperty, initial, BinderBundleStateStrategy)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SerializableBundleStateStrategy<T : Serializable?> : BundleStateStrategy<T>(
    getValue = { key -> getSerializable(key) as T },
    setValue = Bundle::putSerializable
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun <T : Serializable?> state(
            getMirrorProperty: GetViewProperty<T, TView>? = null,
            initial: T
        ): BundleStateDelegateProvider<T, TView> {
            return state(getMirrorProperty, initial, SerializableBundleStateStrategy<T>())
        }
    }
}

object SizeBundleStateStrategy : BundleStateStrategy<Size?>(
    getValue = Bundle::getSize,
    setValue = Bundle::putSize
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetViewProperty<Size?, TView>? = null,
            initial: Size?
        ): BundleStateDelegateProvider<Size?, TView> {
            return state(getMirrorProperty, initial, SizeBundleStateStrategy)
        }
    }
}

object SizeFBundleStateStrategy : BundleStateStrategy<SizeF?>(
    getValue = Bundle::getSizeF,
    setValue = Bundle::putSizeF
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetViewProperty<SizeF?, TView>? = null,
            initial: SizeF?
        ): BundleStateDelegateProvider<SizeF?, TView> {
            return state(getMirrorProperty, initial, SizeFBundleStateStrategy)
        }
    }
}