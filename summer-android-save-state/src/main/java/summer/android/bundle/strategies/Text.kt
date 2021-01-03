package summer.android.bundle.strategies

import android.os.Bundle
import summer.android.bundle.BundleStateProxyProvider
import summer.state.GetViewProperty

class CharSequenceBundleStateStrategy<TView> : BundleStateStrategy<CharSequence?, TView>(
    getValue = Bundle::getCharSequence,
    setValue = Bundle::putCharSequence
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<CharSequence?, TView>? = null,
            initial: CharSequence?,
        ): BundleStateProxyProvider<CharSequence?, TView> {
            return state(getMirrorProperty, initial, CharSequenceBundleStateStrategy())
        }
    }
}

class CharSequenceArrayBundleStateStrategy<TView> : BundleStateStrategy<Array<CharSequence>?, TView>(
    getValue = Bundle::getCharSequenceArray,
    setValue = Bundle::putCharSequenceArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Array<CharSequence>?, TView>? = null,
            initial: Array<CharSequence>?,
        ): BundleStateProxyProvider<Array<CharSequence>?, TView> {
            return state(getMirrorProperty, initial, CharSequenceArrayBundleStateStrategy())
        }
    }
}

class CharSequenceArrayListBundleStateStrategy<TView> : BundleStateStrategy<ArrayList<CharSequence>?, TView>(
    getValue = Bundle::getCharSequenceArrayList,
    setValue = Bundle::putCharSequenceArrayList
)

class StringBundleStateStrategy<TView> : BundleStateStrategy<String, TView>(
    getValue = { key -> getString(key, "") },
    setValue = Bundle::putString
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<String, TView>? = null,
            initial: String,
        ): BundleStateProxyProvider<String, TView> {
            return state(getMirrorProperty, initial, StringBundleStateStrategy())
        }
    }
}

class StringArrayBundleStateStrategy<TView> : BundleStateStrategy<Array<String>?, TView>(
    getValue = Bundle::getStringArray,
    setValue = Bundle::putStringArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Array<String>?, TView>? = null,
            initial: Array<String>?,
        ): BundleStateProxyProvider<Array<String>?, TView> {
            return state(getMirrorProperty, initial, StringArrayBundleStateStrategy())
        }
    }
}

class StringArrayListBundleStateStrategy<TView> : BundleStateStrategy<ArrayList<String>?, TView>(
    getValue = Bundle::getStringArrayList,
    setValue = Bundle::putStringArrayList
)

class NullableStringBundleStateStrategy<TView> : BundleStateStrategy<String?, TView>(
    getValue = Bundle::getString,
    setValue = Bundle::putString
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun nullableState(
            getMirrorProperty: GetViewProperty<String?, TView>? = null,
            initial: String?,
        ): BundleStateProxyProvider<String?, TView> {
            return state(getMirrorProperty, initial, NullableStringBundleStateStrategy())
        }
    }
}