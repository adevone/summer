package summer.android.bundle

import android.os.Bundle
import android.os.Parcelable
import summer.RestoreSummerPresenter
import summer.ViewProvider
import summer.android.bundle.strategies.BinderBundleStateStrategy
import summer.android.bundle.strategies.BooleanArrayBundleStateStrategy
import summer.android.bundle.strategies.BooleanBundleStateStrategy
import summer.android.bundle.strategies.BundleBundleStateStrategy
import summer.android.bundle.strategies.ByteArrayBundleStateStrategy
import summer.android.bundle.strategies.ByteBundleStateStrategy
import summer.android.bundle.strategies.CharArrayBundleStateStrategy
import summer.android.bundle.strategies.CharBundleStateStrategy
import summer.android.bundle.strategies.CharSequenceArrayBundleStateStrategy
import summer.android.bundle.strategies.CharSequenceArrayListBundleStateStrategy
import summer.android.bundle.strategies.CharSequenceBundleStateStrategy
import summer.android.bundle.strategies.DoubleArrayBundleStateStrategy
import summer.android.bundle.strategies.DoubleBundleStateStrategy
import summer.android.bundle.strategies.FloatArrayBundleStateStrategy
import summer.android.bundle.strategies.FloatBundleStateStrategy
import summer.android.bundle.strategies.IntArrayBundleStateStrategy
import summer.android.bundle.strategies.IntArrayListBundleStateStrategy
import summer.android.bundle.strategies.IntBundleStateStrategy
import summer.android.bundle.strategies.LongArrayBundleStateStrategy
import summer.android.bundle.strategies.LongBundleStateStrategy
import summer.android.bundle.strategies.NullableStringBundleStateStrategy
import summer.android.bundle.strategies.ParcelableArrayBundleStateStrategy
import summer.android.bundle.strategies.ParcelableArrayListBundleStateStrategy
import summer.android.bundle.strategies.ParcelableBundleStateStrategy
import summer.android.bundle.strategies.ParcelableSparseArrayBundleStateStrategy
import summer.android.bundle.strategies.SerializableBundleStateStrategy
import summer.android.bundle.strategies.ShortArrayBundleStateStrategy
import summer.android.bundle.strategies.ShortBundleStateStrategy
import summer.android.bundle.strategies.SizeBundleStateStrategy
import summer.android.bundle.strategies.SizeFBundleStateStrategy
import summer.android.bundle.strategies.StringArrayBundleStateStrategy
import summer.android.bundle.strategies.StringArrayListBundleStateStrategy
import summer.android.bundle.strategies.StringBundleStateStrategy
import summer.events.DoExactlyOnceStrategy
import summer.events.DoOnlyWhenAttachedStrategy
import summer.state.GetMirrorProperty

abstract class SaveStateSummerPresenter<TView> :
    RestoreSummerPresenter<TView, BundleProvider, ViewProvider<TView>>(),
    DoOnlyWhenAttachedStrategy.Factory<TView>, DoExactlyOnceStrategy.Factory<TView>,
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
        getMirrorProperty: GetMirrorProperty<TView, ArrayList<String>?>? = null,
        initial: ArrayList<String>?
    ): BundleStateDelegateProvider<ArrayList<String>?> {
        return state(getMirrorProperty, initial, StringArrayListBundleStateStrategy)
    }

    @JvmName("intArrayListState")
    fun state(
        getMirrorProperty: GetMirrorProperty<TView, ArrayList<Int>?>? = null,
        initial: ArrayList<Int>?
    ): BundleStateDelegateProvider<ArrayList<Int>?> {
        return state(getMirrorProperty, initial, IntArrayListBundleStateStrategy)
    }

    @JvmName("charSequenceArrayListState")
    fun state(
        getMirrorProperty: GetMirrorProperty<TView, ArrayList<CharSequence>?>? = null,
        initial: ArrayList<CharSequence>?
    ): BundleStateDelegateProvider<ArrayList<CharSequence>?> {
        return state(getMirrorProperty, initial, CharSequenceArrayListBundleStateStrategy)
    }

    @JvmName("parcelableArrayListState")
    fun <T : Parcelable> state(
        getMirrorProperty: GetMirrorProperty<TView, ArrayList<T>?>? = null,
        initial: ArrayList<T>?
    ): BundleStateDelegateProvider<ArrayList<T>?> {
        return state(getMirrorProperty, initial, ParcelableArrayListBundleStateStrategy<T>())
    }

    override var bundle = Bundle()

    override fun getEventsOwner(): ViewProvider<TView> = this
    override fun getStateOwner(): BundleProvider = this
}