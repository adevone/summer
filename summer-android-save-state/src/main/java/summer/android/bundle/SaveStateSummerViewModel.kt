package summer.android.bundle

import android.os.Bundle
import android.os.Parcelable
import summer.RestoreViewModel
import summer.ViewProxyProvider
import summer.android.bundle.strategies.*
import summer.events.strategies.ExactlyOnceStrategy
import summer.events.strategies.OnlyWhenAttachedStrategy
import summer.state.GetViewProperty

abstract class SaveStateSummerViewModel<TView> :
    RestoreViewModel<TView>(),
    ViewProxyProvider<TView>,
    OnlyWhenAttachedStrategy.ProxyFactory<TView>, ExactlyOnceStrategy.ProxyFactory<TView>,
    IntBundleStateStrategy.Factory<TView>, IntArrayBundleStateStrategy.Factory<TView>,
    BooleanBundleStateStrategy.Factory<TView>, BooleanArrayBundleStateStrategy.Factory<TView>,
    LongBundleStateStrategy.Factory<TView>, LongArrayBundleStateStrategy.Factory<TView>,
    ShortBundleStateStrategy.Factory<TView>, ShortArrayBundleStateStrategy.Factory<TView>,
    FloatBundleStateStrategy.Factory<TView>, FloatArrayBundleStateStrategy.Factory<TView>,
    DoubleBundleStateStrategy.Factory<TView>, DoubleArrayBundleStateStrategy.Factory<TView>,
    BinderBundleStateStrategy.Factory<TView>,
    CharBundleStateStrategy.Factory<TView>, CharArrayBundleStateStrategy.Factory<TView>,
    ByteBundleStateStrategy.Factory<TView>, ByteArrayBundleStateStrategy.Factory<TView>,
    CharSequenceBundleStateStrategy.Factory<TView>, CharSequenceArrayBundleStateStrategy.Factory<TView>,
    BundleBundleStateStrategy.Factory<TView>,
    ParcelableBundleStateStrategy.Factory<TView>, ParcelableArrayBundleStateStrategy.Factory<TView>,
    ParcelableSparseArrayBundleStateStrategy.Factory<TView>,
    SerializableBundleStateStrategy.Factory<TView>,
    SizeBundleStateStrategy.Factory<TView>, SizeFBundleStateStrategy.Factory<TView>,
    StringBundleStateStrategy.Factory<TView>, StringArrayBundleStateStrategy.Factory<TView>,
    NullableStringBundleStateStrategy.Factory<TView>,
    BundleProvider {

    @JvmName("stringArrayListState")
    fun state(
        getMirrorProperty: GetViewProperty<ArrayList<String>?, TView>? = null,
        initial: ArrayList<String>?,
    ): BundleStateProxyProvider<ArrayList<String>?, TView> {
        return state(getMirrorProperty, initial, StringArrayListBundleStateStrategy())
    }

    @JvmName("intArrayListState")
    fun state(
        getMirrorProperty: GetViewProperty<ArrayList<Int>?, TView>? = null,
        initial: ArrayList<Int>?,
    ): BundleStateProxyProvider<ArrayList<Int>?, TView> {
        return state(getMirrorProperty, initial, IntArrayListBundleStateStrategy())
    }

    @JvmName("charSequenceArrayListState")
    fun state(
        getMirrorProperty: GetViewProperty<ArrayList<CharSequence>?, TView>? = null,
        initial: ArrayList<CharSequence>?,
    ): BundleStateProxyProvider<ArrayList<CharSequence>?, TView> {
        return state(getMirrorProperty, initial, CharSequenceArrayListBundleStateStrategy())
    }

    @JvmName("parcelableArrayListState")
    fun <T : Parcelable> state(
        getMirrorProperty: GetViewProperty<ArrayList<T>?, TView>? = null,
        initial: ArrayList<T>?,
    ): BundleStateProxyProvider<ArrayList<T>?, TView> {
        return state(getMirrorProperty, initial, ParcelableArrayListBundleStateStrategy())
    }

    override var bundle = Bundle()
}