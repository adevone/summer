package summer.android.bundle.strategies

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateDelegateProvider
import summer.state.GetViewProperty
import summer.state.StateFactory

@Suppress("UNCHECKED_CAST")
class ParcelableBundleStateStrategy<T : Parcelable?> : BundleStateStrategy<T>(
    getValue = { key -> getParcelable<T>(key) as T },
    setValue = Bundle::putParcelable
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun <T : Parcelable?> state(
            getMirrorProperty: GetViewProperty<T, TView>? = null,
            initial: T
        ): BundleStateDelegateProvider<T, TView> {
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
            getMirrorProperty: GetViewProperty<Array<T>?, TView>? = null,
            initial: Array<T>?
        ): BundleStateDelegateProvider<Array<T>?, TView> {
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
            getMirrorProperty: GetViewProperty<SparseArray<T>?, TView>? = null,
            initial: SparseArray<T>?
        ): BundleStateDelegateProvider<SparseArray<T>?, TView> {
            return state(getMirrorProperty, initial, ParcelableSparseArrayBundleStateStrategy<T>())
        }
    }
}