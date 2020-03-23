package summer.android.bundle.strategies

import android.os.Bundle
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateDelegateProvider
import summer.state.GetMirrorProperty
import summer.state.StateFactory

object IntBundleStateStrategy : BundleStateStrategy<Int>(
    getValue = Bundle::getInt,
    setValue = Bundle::putInt
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Int>? = null,
            initial: Int
        ): BundleStateDelegateProvider<Int> {
            return state(getMirrorProperty, initial, IntBundleStateStrategy)
        }
    }
}

object IntArrayBundleStateStrategy : BundleStateStrategy<IntArray?>(
    getValue = Bundle::getIntArray,
    setValue = Bundle::putIntArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, IntArray?>? = null,
            initial: IntArray?
        ): BundleStateDelegateProvider<IntArray?> {
            return state(getMirrorProperty, initial, IntArrayBundleStateStrategy)
        }
    }
}

object IntArrayListBundleStateStrategy : BundleStateStrategy<ArrayList<Int>?>(
    getValue = Bundle::getIntegerArrayList,
    setValue = Bundle::putIntegerArrayList
)

object BooleanBundleStateStrategy : BundleStateStrategy<Boolean>(
    getValue = { key -> getBoolean(key) },
    setValue = { key, value -> putBoolean(key, value) }
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

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
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, BooleanArray?>? = null,
            initial: BooleanArray?
        ): BundleStateDelegateProvider<BooleanArray?> {
            return state(getMirrorProperty, initial, BooleanArrayBundleStateStrategy)
        }
    }
}

object LongBundleStateStrategy : BundleStateStrategy<Long>(
    getValue = Bundle::getLong,
    setValue = Bundle::putLong
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Long>? = null,
            initial: Long
        ): BundleStateDelegateProvider<Long> {
            return state(getMirrorProperty, initial, LongBundleStateStrategy)
        }
    }
}

object LongArrayBundleStateStrategy : BundleStateStrategy<LongArray?>(
    getValue = Bundle::getLongArray,
    setValue = Bundle::putLongArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, LongArray?>? = null,
            initial: LongArray?
        ): BundleStateDelegateProvider<LongArray?> {
            return state(getMirrorProperty, initial, LongArrayBundleStateStrategy)
        }
    }
}

object ShortBundleStateStrategy : BundleStateStrategy<Short>(
    getValue = Bundle::getShort,
    setValue = Bundle::putShort
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Short>? = null,
            initial: Short
        ): BundleStateDelegateProvider<Short> {
            return state(getMirrorProperty, initial, ShortBundleStateStrategy)
        }
    }
}

object ShortArrayBundleStateStrategy : BundleStateStrategy<ShortArray?>(
    getValue = Bundle::getShortArray,
    setValue = Bundle::putShortArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, ShortArray?>? = null,
            initial: ShortArray?
        ): BundleStateDelegateProvider<ShortArray?> {
            return state(getMirrorProperty, initial, ShortArrayBundleStateStrategy)
        }
    }
}

object FloatBundleStateStrategy : BundleStateStrategy<Float>(
    getValue = Bundle::getFloat,
    setValue = Bundle::putFloat
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Float>? = null,
            initial: Float
        ): BundleStateDelegateProvider<Float> {
            return state(getMirrorProperty, initial, FloatBundleStateStrategy)
        }
    }
}

object FloatArrayBundleStateStrategy : BundleStateStrategy<FloatArray?>(
    getValue = Bundle::getFloatArray,
    setValue = Bundle::putFloatArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, FloatArray?>? = null,
            initial: FloatArray?
        ): BundleStateDelegateProvider<FloatArray?> {
            return state(getMirrorProperty, initial, FloatArrayBundleStateStrategy)
        }
    }
}

object DoubleBundleStateStrategy : BundleStateStrategy<Double>(
    getValue = Bundle::getDouble,
    setValue = Bundle::putDouble
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Double>? = null,
            initial: Double
        ): BundleStateDelegateProvider<Double> {
            return state(getMirrorProperty, initial, DoubleBundleStateStrategy)
        }
    }
}

object DoubleArrayBundleStateStrategy : BundleStateStrategy<DoubleArray?>(
    getValue = Bundle::getDoubleArray,
    setValue = Bundle::putDoubleArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, DoubleArray?>? = null,
            initial: DoubleArray?
        ): BundleStateDelegateProvider<DoubleArray?> {
            return state(getMirrorProperty, initial, DoubleArrayBundleStateStrategy)
        }
    }
}

object CharBundleStateStrategy : BundleStateStrategy<Char>(
    getValue = Bundle::getChar,
    setValue = Bundle::putChar
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Char>? = null,
            initial: Char
        ): BundleStateDelegateProvider<Char> {
            return state(getMirrorProperty, initial, CharBundleStateStrategy)
        }
    }
}

object CharArrayBundleStateStrategy : BundleStateStrategy<CharArray?>(
    getValue = Bundle::getCharArray,
    setValue = Bundle::putCharArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, CharArray?>? = null,
            initial: CharArray?
        ): BundleStateDelegateProvider<CharArray?> {
            return state(getMirrorProperty, initial, CharArrayBundleStateStrategy)
        }
    }
}

object ByteBundleStateStrategy : BundleStateStrategy<Byte>(
    getValue = Bundle::getByte,
    setValue = Bundle::putByte
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, Byte>? = null,
            initial: Byte
        ): BundleStateDelegateProvider<Byte> {
            return state(getMirrorProperty, initial, ByteBundleStateStrategy)
        }
    }
}

object ByteArrayBundleStateStrategy : BundleStateStrategy<ByteArray?>(
    getValue = Bundle::getByteArray,
    setValue = Bundle::putByteArray
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetMirrorProperty<TView, ByteArray?>? = null,
            initial: ByteArray?
        ): BundleStateDelegateProvider<ByteArray?> {
            return state(getMirrorProperty, initial, ByteArrayBundleStateStrategy)
        }
    }
}