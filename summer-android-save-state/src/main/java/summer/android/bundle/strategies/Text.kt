package summer.android.bundle.strategies

import android.os.Bundle
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateDelegateProvider
import summer.state.GetViewProperty
import summer.state.StateProxyFactory

object CharSequenceBundleStateStrategy : BundleStateStrategy<CharSequence?>(
    getValue = Bundle::getCharSequence,
    setValue = Bundle::putCharSequence
) {
    interface Factory<TView> : StateProxyFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetViewProperty<CharSequence?, TView>? = null,
            initial: CharSequence?
        ): BundleStateDelegateProvider<CharSequence?, TView> {
            return state(getMirrorProperty, initial, CharSequenceBundleStateStrategy)
        }
    }
}

object CharSequenceArrayBundleStateStrategy : BundleStateStrategy<Array<CharSequence>?>(
    getValue = Bundle::getCharSequenceArray,
    setValue = Bundle::putCharSequenceArray
) {
    interface Factory<TView> : StateProxyFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetViewProperty<Array<CharSequence>?, TView>? = null,
            initial: Array<CharSequence>?
        ): BundleStateDelegateProvider<Array<CharSequence>?, TView> {
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
    interface Factory<TView> : StateProxyFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetViewProperty<String, TView>? = null,
            initial: String
        ): BundleStateDelegateProvider<String, TView> {
            return state(getMirrorProperty, initial, StringBundleStateStrategy)
        }
    }
}

object StringArrayBundleStateStrategy : BundleStateStrategy<Array<String>?>(
    getValue = Bundle::getStringArray,
    setValue = Bundle::putStringArray
) {
    interface Factory<TView> : StateProxyFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetViewProperty<Array<String>?, TView>? = null,
            initial: Array<String>?
        ): BundleStateDelegateProvider<Array<String>?, TView> {
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
    interface Factory<TView> : StateProxyFactory<TView, BundleProvider> {

        fun nullableState(
            getMirrorProperty: GetViewProperty<String?, TView>? = null,
            initial: String?
        ): BundleStateDelegateProvider<String?, TView> {
            return state(getMirrorProperty, initial, NullableStringBundleStateStrategy)
        }
    }
}