package summer.android.bundle

import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import summer.state.GetMirrorProperty
import summer.state.StateDelegateProvider
import summer.state.StateFactory
import summer.state.SummerStateStrategy
import java.io.Serializable
import kotlin.reflect.KProperty

object IntBundleStateStrategy : BundleStateStrategy<Int>(Bundle::getInt, Bundle::putInt) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Int>? = null,
            initial: Int
        ): BundleStateDelegateProvider<Int> {
            return state(getMirrorProperty, initial, IntBundleStateStrategy)
        }
    }
}

object IntArrayBundleStateStrategy : BundleStateStrategy<IntArray?>(Bundle::getIntArray, Bundle::putIntArray) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, IntArray?>? = null,
            initial: IntArray?
        ): BundleStateDelegateProvider<IntArray?> {
            return state(getMirrorProperty, initial, IntArrayBundleStateStrategy)
        }
    }
}

object IntArrayListBundleStateStrategy : BundleStateStrategy<ArrayList<Int>?>(Bundle::getIntegerArrayList, Bundle::putIntegerArrayList)

object BooleanBundleStateStrategy : BundleStateStrategy<Boolean>(
    getValue = { key -> getBoolean(key) },
    setValue = { key, value -> putBoolean(key, value) }
) {
    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Boolean>? = null,
            initial: Boolean
        ): BundleStateDelegateProvider<Boolean> {
            return state(getMirrorProperty, initial, BooleanBundleStateStrategy)
        }
    }
}

object BooleanArrayBundleStateStrategy : BundleStateStrategy<BooleanArray?>(
    getValue = { key -> getBooleanArray(key) },
    setValue = { key, value -> putBooleanArray(key, value) }
) {
    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, BooleanArray?>? = null,
            initial: BooleanArray?
        ): BundleStateDelegateProvider<BooleanArray?> {
            return state(getMirrorProperty, initial, BooleanArrayBundleStateStrategy)
        }
    }
}

object LongBundleStateStrategy : BundleStateStrategy<Long>(Bundle::getLong, Bundle::putLong) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Long>? = null,
            initial: Long
        ): BundleStateDelegateProvider<Long> {
            return state(getMirrorProperty, initial, LongBundleStateStrategy)
        }
    }
}

object LongArrayBundleStateStrategy : BundleStateStrategy<LongArray?>(Bundle::getLongArray, Bundle::putLongArray) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, LongArray?>? = null,
            initial: LongArray?
        ): BundleStateDelegateProvider<LongArray?> {
            return state(getMirrorProperty, initial, LongArrayBundleStateStrategy)
        }
    }
}

object ShortBundleStateStrategy : BundleStateStrategy<Short>(Bundle::getShort, Bundle::putShort) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Short>? = null,
            initial: Short
        ): BundleStateDelegateProvider<Short> {
            return state(getMirrorProperty, initial, ShortBundleStateStrategy)
        }
    }
}

object ShortArrayBundleStateStrategy : BundleStateStrategy<ShortArray?>(Bundle::getShortArray, Bundle::putShortArray) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, ShortArray?>? = null,
            initial: ShortArray?
        ): BundleStateDelegateProvider<ShortArray?> {
            return state(getMirrorProperty, initial, ShortArrayBundleStateStrategy)
        }
    }
}

object FloatBundleStateStrategy : BundleStateStrategy<Float>(Bundle::getFloat, Bundle::putFloat) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Float>? = null,
            initial: Float
        ): BundleStateDelegateProvider<Float> {
            return state(getMirrorProperty, initial, FloatBundleStateStrategy)
        }
    }
}

object FloatArrayBundleStateStrategy : BundleStateStrategy<FloatArray?>(Bundle::getFloatArray, Bundle::putFloatArray) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, FloatArray?>? = null,
            initial: FloatArray?
        ): BundleStateDelegateProvider<FloatArray?> {
            return state(getMirrorProperty, initial, FloatArrayBundleStateStrategy)
        }
    }
}

object DoubleBundleStateStrategy : BundleStateStrategy<Double>(Bundle::getDouble, Bundle::putDouble) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Double>? = null,
            initial: Double
        ): BundleStateDelegateProvider<Double> {
            return state(getMirrorProperty, initial, DoubleBundleStateStrategy)
        }
    }
}

object DoubleArrayBundleStateStrategy : BundleStateStrategy<DoubleArray?>(Bundle::getDoubleArray, Bundle::putDoubleArray) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, DoubleArray?>? = null,
            initial: DoubleArray?
        ): BundleStateDelegateProvider<DoubleArray?> {
            return state(getMirrorProperty, initial, DoubleArrayBundleStateStrategy)
        }
    }
}

object BinderBundleStateStrategy : BundleStateStrategy<IBinder?>(Bundle::getBinder, Bundle::putBinder) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, IBinder?>? = null,
            initial: IBinder?
        ): BundleStateDelegateProvider<IBinder?> {
            return state(getMirrorProperty, initial, BinderBundleStateStrategy)
        }
    }
}

object CharBundleStateStrategy : BundleStateStrategy<Char>(Bundle::getChar, Bundle::putChar) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Char>? = null,
            initial: Char
        ): BundleStateDelegateProvider<Char> {
            return state(getMirrorProperty, initial, CharBundleStateStrategy)
        }
    }
}

object CharArrayBundleStateStrategy : BundleStateStrategy<CharArray?>(Bundle::getCharArray, Bundle::putCharArray) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, CharArray?>? = null,
            initial: CharArray?
        ): BundleStateDelegateProvider<CharArray?> {
            return state(getMirrorProperty, initial, CharArrayBundleStateStrategy)
        }
    }
}

object ByteBundleStateStrategy : BundleStateStrategy<Byte>(Bundle::getByte, Bundle::putByte) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Byte>? = null,
            initial: Byte
        ): BundleStateDelegateProvider<Byte> {
            return state(getMirrorProperty, initial, ByteBundleStateStrategy)
        }
    }
}

object ByteArrayBundleStateStrategy : BundleStateStrategy<ByteArray?>(Bundle::getByteArray, Bundle::putByteArray) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, ByteArray?>? = null,
            initial: ByteArray?
        ): BundleStateDelegateProvider<ByteArray?> {
            return state(getMirrorProperty, initial, ByteArrayBundleStateStrategy)
        }
    }
}

object CharSequenceBundleStateStrategy : BundleStateStrategy<CharSequence?>(Bundle::getCharSequence, Bundle::putCharSequence) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, CharSequence?>? = null,
            initial: CharSequence?
        ): BundleStateDelegateProvider<CharSequence?> {
            return state(getMirrorProperty, initial, CharSequenceBundleStateStrategy)
        }
    }
}

object CharSequenceArrayBundleStateStrategy : BundleStateStrategy<Array<CharSequence>?>(Bundle::getCharSequenceArray, Bundle::putCharSequenceArray) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Array<CharSequence>?>? = null,
            initial: Array<CharSequence>?
        ): BundleStateDelegateProvider<Array<CharSequence>?> {
            return state(getMirrorProperty, initial, CharSequenceArrayBundleStateStrategy)
        }
    }
}

object CharSequenceArrayListBundleStateStrategy : BundleStateStrategy<ArrayList<CharSequence>?>(Bundle::getCharSequenceArrayList, Bundle::putCharSequenceArrayList)

object BundleBundleStateStrategy : BundleStateStrategy<Bundle?>(Bundle::getBundle, Bundle::putBundle) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Bundle?>? = null,
            initial: Bundle?
        ): BundleStateDelegateProvider<Bundle?> {
            return state(getMirrorProperty, initial, BundleBundleStateStrategy)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ParcelableBundleStateStrategy<T : Parcelable?> : BundleStateStrategy<T>(
    getValue = { key -> getParcelable<T>(key) as T },
    setValue = Bundle::putParcelable
) {
    interface Factory<TView> : StateFactory<BundleProvider, TView> {

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
    interface Factory<TView> : StateFactory<BundleProvider, TView> {

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
    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun <T : Parcelable> state(
            getMirrorProperty: GetMirrorProperty<TView, SparseArray<T>?>? = null,
            initial: SparseArray<T>?
        ): BundleStateDelegateProvider<SparseArray<T>?> {
            return state(getMirrorProperty, initial, ParcelableSparseArrayBundleStateStrategy<T>())
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SerializableBundleStateStrategy<T : Serializable?> : BundleStateStrategy<T>(
    getValue = { key -> getSerializable(key) as T },
    setValue = Bundle::putSerializable
) {
    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun <T : Serializable?> state(
            getMirrorProperty: GetMirrorProperty<TView, T>? = null,
            initial: T
        ): BundleStateDelegateProvider<T> {
            return state(getMirrorProperty, initial, SerializableBundleStateStrategy<T>())
        }
    }
}

object SizeBundleStateStrategy : BundleStateStrategy<Size?>(Bundle::getSize, Bundle::putSize) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Size?>? = null,
            initial: Size?
        ): BundleStateDelegateProvider<Size?> {
            return state(getMirrorProperty, initial, SizeBundleStateStrategy)
        }
    }
}

object SizeFBundleStateStrategy : BundleStateStrategy<SizeF?>(Bundle::getSizeF, Bundle::putSizeF) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, SizeF?>? = null,
            initial: SizeF?
        ): BundleStateDelegateProvider<SizeF?> {
            return state(getMirrorProperty, initial, SizeFBundleStateStrategy)
        }
    }
}

object StringBundleStateStrategy : BundleStateStrategy<String>({ key -> getString(key, "") }, Bundle::putString) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, String>? = null,
            initial: String
        ): BundleStateDelegateProvider<String> {
            return state(getMirrorProperty, initial, StringBundleStateStrategy)
        }
    }
}

object StringArrayBundleStateStrategy : BundleStateStrategy<Array<String>?>(Bundle::getStringArray, Bundle::putStringArray) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Array<String>?>? = null,
            initial: Array<String>?
        ): BundleStateDelegateProvider<Array<String>?> {
            return state(getMirrorProperty, initial, StringArrayBundleStateStrategy)
        }
    }
}

object StringArrayListBundleStateStrategy : BundleStateStrategy<ArrayList<String>?>(Bundle::getStringArrayList, Bundle::putStringArrayList)

object NullableStringBundleStateStrategy : BundleStateStrategy<String?>(Bundle::getString, Bundle::putString) {

    interface Factory<TView> : StateFactory<BundleProvider, TView> {

        fun nullableState(
            getMirrorProperty: GetMirrorProperty<TView, String?>? = null,
            initial: String?
        ): BundleStateDelegateProvider<String?> {
            return state(getMirrorProperty, initial, NullableStringBundleStateStrategy)
        }
    }
}

abstract class BundleStateStrategy<T>(
    private val getValue: Bundle.(String?) -> T,
    private val setValue: Bundle.(String?, T) -> Unit
) : SummerStateStrategy<BundleProvider, T> {

    override fun get(owner: BundleProvider, prop: KProperty<*>): T {
        return owner.bundle.getValue(prop.name)
    }

    override fun isInit(owner: BundleProvider, prop: KProperty<*>): Boolean {
        return owner.bundle.containsKey(prop.name)
    }

    override fun set(owner: BundleProvider, prop: KProperty<*>, value: T) {
        owner.bundle.setValue(prop.name, value)
    }
}

typealias BundleStateDelegateProvider<T> = StateDelegateProvider<T, BundleProvider>

interface BundleProvider {
    val bundle: Bundle
}