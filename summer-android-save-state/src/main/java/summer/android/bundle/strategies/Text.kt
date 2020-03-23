package summer.android.bundle.strategies

import android.os.Bundle
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateDelegateProvider
import summer.state.GetMirrorProperty
import summer.state.StateFactory

object CharSequenceBundleStateStrategy : BundleStateStrategy<CharSequence?>(
    getValue = Bundle::getCharSequence,
    setValue = Bundle::putCharSequence
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, CharSequence?>? = null,
            initial: CharSequence?
        ): BundleStateDelegateProvider<CharSequence?> {
            return state(getMirrorProperty, initial, CharSequenceBundleStateStrategy)
        }
    }
}

object CharSequenceArrayBundleStateStrategy : BundleStateStrategy<Array<CharSequence>?>(
    getValue = Bundle::getCharSequenceArray,
    setValue = Bundle::putCharSequenceArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Array<CharSequence>?>? = null,
            initial: Array<CharSequence>?
        ): BundleStateDelegateProvider<Array<CharSequence>?> {
            return state(getMirrorProperty, initial, CharSequenceArrayBundleStateStrategy)
        }
    }
}

object CharSequenceArrayListBundleStateStrategy : BundleStateStrategy<ArrayList<CharSequence>?>(
    getValue = Bundle::getCharSequenceArrayList,
    setValue = Bundle::putCharSequenceArrayList
)

object StringBundleStateStrategy : BundleStateStrategy<String>(
    getValue = { key -> getString(key, "") },
    setValue = Bundle::putString
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, String>? = null,
            initial: String
        ): BundleStateDelegateProvider<String> {
            return state(getMirrorProperty, initial, StringBundleStateStrategy)
        }
    }
}

object StringArrayBundleStateStrategy : BundleStateStrategy<Array<String>?>(
    getValue = Bundle::getStringArray,
    setValue = Bundle::putStringArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Array<String>?>? = null,
            initial: Array<String>?
        ): BundleStateDelegateProvider<Array<String>?> {
            return state(getMirrorProperty, initial, StringArrayBundleStateStrategy)
        }
    }
}

object StringArrayListBundleStateStrategy : BundleStateStrategy<ArrayList<String>?>(
    getValue = Bundle::getStringArrayList,
    setValue = Bundle::putStringArrayList
)

object NullableStringBundleStateStrategy : BundleStateStrategy<String?>(
    getValue = Bundle::getString,
    setValue = Bundle::putString
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun nullableState(
            getMirrorProperty: GetMirrorProperty<TView, String?>? = null,
            initial: String?
        ): BundleStateDelegateProvider<String?> {
            return state(getMirrorProperty, initial, NullableStringBundleStateStrategy)
        }
    }
}