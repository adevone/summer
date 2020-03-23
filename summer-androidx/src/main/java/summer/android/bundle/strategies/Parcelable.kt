package summer.android.bundle.strategies

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateDelegateProvider
import summer.state.GetMirrorProperty
import summer.state.StateFactory

@Suppress("UNCHECKED_CAST")
class ParcelableBundleStateStrategy<T : Parcelable?> : BundleStateStrategy<T>(
    getValue = { key -> getParcelable<T>(key) as T },
    setValue = Bundle::putParcelable
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun <T : Parcelable?> state(
            getMirrorProperty: GetMirrorProperty<TView, T>? = null,
            initial: T
        ): BundleStateDelegateProvider<T> {
            return state(getMirrorProperty, initial, ParcelableBundleStateStrategy())
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ParcelableArrayBundleStateStrategy<T : Parcelable?> : BundleStateStrategy<Array<T>?>(
    getValue = { key -> getParcelableArray(key) as Array<T> },
    setValue = Bundle::putParcelableArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun <T : Parcelable> state(
            getMirrorProperty: GetMirrorProperty<TView, Array<T>?>? = null,
            initial: Array<T>?
        ): BundleStateDelegateProvider<Array<T>?> {
            return state(getMirrorProperty, initial, ParcelableArrayBundleStateStrategy<T>())
        }
    }
}

class ParcelableArrayListBundleStateStrategy<T : Parcelable> : BundleStateStrategy<ArrayList<T>?>(
    getValue = { key -> getParcelableArrayList<T>(key) as ArrayList<T> },
    setValue = Bundle::putParcelableArrayList
)

class ParcelableSparseArrayBundleStateStrategy<T : Parcelable> : BundleStateStrategy<SparseArray<T>?>(
    getValue = { key -> getSparseParcelableArray<T>(key) as SparseArray<T> },
    setValue = Bundle::putSparseParcelableArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun <T : Parcelable> state(
            getMirrorProperty: GetMirrorProperty<TView, SparseArray<T>?>? = null,
            initial: SparseArray<T>?
        ): BundleStateDelegateProvider<SparseArray<T>?> {
            return state(getMirrorProperty, initial, ParcelableSparseArrayBundleStateStrategy<T>())
        }
    }
}