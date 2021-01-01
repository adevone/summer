package summer.android.bundle.strategies

import android.os.Bundle
import summer.android.bundle.BundleProvider
import summer.android.bundle.BundleStateDelegateProvider
import summer.state.GetViewProperty
import summer.state.StateFactory

object IntBundleStateStrategy : BundleStateStrategy<Int>(
    getValue = Bundle::getInt,
    setValue = Bundle::putInt
) {
    interface Factory<TView> : StateFactory<TView, BundleProvider> {

        fun state(
            getMirrorProperty: GetViewProperty<Int, TView>? = null,
            initial: Int
        ): BundleStateDelegateProvider<Int, TView> {
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
            getMirrorProperty: GetViewProperty<IntArray?, TView>? = null,
            initial: IntArray?
        ): BundleStateDelegateProvider<IntArray?, TView> {
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
            getMirrorProperty: GetViewProperty<Boolean, TView>? = null,
            initial: Boolean
        ): BundleStateDelegateProvider<Boolean, TView> {
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
            getMirrorProperty: GetViewProperty<BooleanArray?, TView>? = null,
            initial: BooleanArray?
        ): BundleStateDelegateProvider<BooleanArray?, TView> {
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
            getMirrorProperty: GetViewProperty<Long, TView>? = null,
            initial: Long
        ): BundleStateDelegateProvider<Long, TView> {
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
            getMirrorProperty: GetViewProperty<LongArray?, TView>? = null,
            initial: LongArray?
        ): BundleStateDelegateProvider<LongArray?, TView> {
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
            getMirrorProperty: GetViewProperty<Short, TView>? = null,
            initial: Short
        ): BundleStateDelegateProvider<Short, TView> {
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
            getMirrorProperty: GetViewProperty<ShortArray?, TView>? = null,
            initial: ShortArray?
        ): BundleStateDelegateProvider<ShortArray?, TView> {
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
            getMirrorProperty: GetViewProperty<Float, TView>? = null,
            initial: Float
        ): BundleStateDelegateProvider<Float, TView> {
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
            getMirrorProperty: GetViewProperty<FloatArray?, TView>? = null,
            initial: FloatArray?
        ): BundleStateDelegateProvider<FloatArray?, TView> {
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
            getMirrorProperty: GetViewProperty<Double, TView>? = null,
            initial: Double
        ): BundleStateDelegateProvider<Double, TView> {
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
            getMirrorProperty: GetViewProperty<DoubleArray?, TView>? = null,
            initial: DoubleArray?
        ): BundleStateDelegateProvider<DoubleArray?, TView> {
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
            getMirrorProperty: GetViewProperty<Char, TView>? = null,
            initial: Char
        ): BundleStateDelegateProvider<Char, TView> {
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
            getMirrorProperty: GetViewProperty<CharArray?, TView>? = null,
            initial: CharArray?
        ): BundleStateDelegateProvider<CharArray?, TView> {
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
            getMirrorProperty: GetViewProperty<Byte, TView>? = null,
            initial: Byte
        ): BundleStateDelegateProvider<Byte, TView> {
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
            getMirrorProperty: GetViewProperty<ByteArray?, TView>? = null,
            initial: ByteArray?
        ): BundleStateDelegateProvider<ByteArray?, TView> {
            return state(getMirrorProperty, initial, ByteArrayBundleStateStrategy)
        }
    }
}