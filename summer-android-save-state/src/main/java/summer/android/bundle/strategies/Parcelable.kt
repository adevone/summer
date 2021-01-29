package summer.android.bundle.strategies

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import summer.android.bundle.BundleStateProxyProvider
import summer.state.GetViewProperty

@Suppress("UNCHECKED_CAST")
class ParcelableBundleStateStrategy<T : Parcelable?, TView> : BundleStateStrategy<T, TView>(
    getValue = { key -> getParcelable<T>(key) as T },
    setValue = Bundle::putParcelable
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun <T : Parcelable?> state(
            getMirrorProperty: GetViewProperty<T, TView>? = null,
            initial: T,
        ): BundleStateProxyProvider<T, TView> {
            return state(getMirrorProperty, initial, ParcelableBundleStateStrategy())
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ParcelableArrayBundleStateStrategy<T : Parcelable?, TView> : BundleStateStrategy<Array<T>?, TView>(
    getValue = { key -> getParcelableArray(key) as Array<T> },
    setValue = Bundle::putParcelableArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun <T : Parcelable> state(
            getMirrorProperty: GetViewProperty<Array<T>?, TView>? = null,
            initial: Array<T>?,
        ): BundleStateProxyProvider<Array<T>?, TView> {
            return state(getMirrorProperty, initial, ParcelableArrayBundleStateStrategy())
        }
    }
}

class ParcelableArrayListBundleStateStrategy<T : Parcelable, TView> : BundleStateStrategy<ArrayList<T>?, TView>(
    getValue = { key -> getParcelableArrayList<T>(key) as ArrayList<T> },
    setValue = Bundle::putParcelableArrayList
)

class ParcelableSparseArrayBundleStateStrategy<T : Parcelable, TView> : BundleStateStrategy<SparseArray<T>?, TView>(
    getValue = { key -> getSparseParcelableArray<T>(key) as SparseArray<T> },
    setValue = Bundle::putSparseParcelableArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun <T : Parcelable> state(
            getMirrorProperty: GetViewProperty<SparseArray<T>?, TView>? = null,
            initial: SparseArray<T>?,
        ): BundleStateProxyProvider<SparseArray<T>?, TView> {
            return state(getMirrorProperty, initial, ParcelableSparseArrayBundleStateStrategy())
        }
    }
}