package summer.android.bundle.strategies

import android.os.Bundle
import android.os.IBinder
import android.util.Size
import android.util.SizeF
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateDelegateProvider
import summer.state.GetMirrorProperty
import summer.state.StateFactory
import java.io.Serializable

object BinderBundleStateStrategy : BundleStateStrategy<IBinder?>(
    getValue = Bundle::getBinder,
    setValue = Bundle::putBinder
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, IBinder?>? = null,
            initial: IBinder?
        ): BundleStateDelegateProvider<IBinder?> {
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
            getMirrorProperty: GetMirrorProperty<TView, T>? = null,
            initial: T
        ): BundleStateDelegateProvider<T> {
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
            getMirrorProperty: GetMirrorProperty<TView, Size?>? = null,
            initial: Size?
        ): BundleStateDelegateProvider<Size?> {
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
            getMirrorProperty: GetMirrorProperty<TView, SizeF?>? = null,
            initial: SizeF?
        ): BundleStateDelegateProvider<SizeF?> {
            return state(getMirrorProperty, initial, SizeFBundleStateStrategy)
        }
    }
}