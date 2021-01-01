package summer.android.bundle

import android.os.Bundle
import android.os.Parcelable
import summer.GetViewProvider
import summer.RestoreViewModel
import summer.android.bundle.strategies.*
import summer.events.DoExactlyOnceStrategy
import summer.events.DoOnlyWhenAttachedStrategy
import summer.state.GetMirrorProperty

abstract class SaveStateSummerViewModel<TView> :
    RestoreViewModel<TView, BundleProvider, GetViewProvider<TView>>(),
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

    override fun getEventsOwner(): GetViewProvider<TView> = this
    override fun getStateOwner(): BundleProvider = this
}