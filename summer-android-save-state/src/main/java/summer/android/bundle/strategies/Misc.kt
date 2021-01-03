package summer.android.bundle.strategies

import android.os.Bundle
import android.os.IBinder
import android.util.Size
import android.util.SizeF
import summer.android.bundle.BundleStateProxyProvider
import summer.state.GetViewProperty
import java.io.Serializable

class BinderBundleStateStrategy<TView> : BundleStateStrategy<IBinder?, TView>(
    getValue = Bundle::getBinder,
    setValue = Bundle::putBinder
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<IBinder?, TView>? = null,
            initial: IBinder?,
        ): BundleStateProxyProvider<IBinder?, TView> {
            return state(getMirrorProperty, initial, BinderBundleStateStrategy())
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SerializableBundleStateStrategy<T : Serializable?, TView> : BundleStateStrategy<T, TView>(
    getValue = { key -> getSerializable(key) as T },
    setValue = Bundle::putSerializable
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun <T : Serializable?> state(
            getMirrorProperty: GetViewProperty<T, TView>? = null,
            initial: T,
        ): BundleStateProxyProvider<T, TView> {
            return state(getMirrorProperty, initial, SerializableBundleStateStrategy())
        }
    }
}

class SizeBundleStateStrategy<TView> : BundleStateStrategy<Size?, TView>(
    getValue = Bundle::getSize,
    setValue = Bundle::putSize
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Size?, TView>? = null,
            initial: Size?,
        ): BundleStateProxyProvider<Size?, TView> {
            return state(getMirrorProperty, initial, SizeBundleStateStrategy())
        }
    }
}

class SizeFBundleStateStrategy<TView> : BundleStateStrategy<SizeF?, TView>(
    getValue = Bundle::getSizeF,
    setValue = Bundle::putSizeF
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<SizeF?, TView>? = null,
            initial: SizeF?,
        ): BundleStateProxyProvider<SizeF?, TView> {
            return state(getMirrorProperty, initial, SizeFBundleStateStrategy())
        }
    }
}