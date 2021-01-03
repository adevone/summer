package summer.android.bundle.strategies

import android.os.Bundle
import summer.android.bundle.BundleStateProxyProvider
import summer.state.GetViewProperty

class IntBundleStateStrategy<TView> : BundleStateStrategy<Int, TView>(
    getValue = Bundle::getInt,
    setValue = Bundle::putInt
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Int, TView>? = null,
            initial: Int,
        ): BundleStateProxyProvider<Int, TView> {
            return state(getMirrorProperty, initial, IntBundleStateStrategy())
        }
    }
}

class IntArrayBundleStateStrategy<TView> : BundleStateStrategy<IntArray?, TView>(
    getValue = Bundle::getIntArray,
    setValue = Bundle::putIntArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<IntArray?, TView>? = null,
            initial: IntArray?,
        ): BundleStateProxyProvider<IntArray?, TView> {
            return state(getMirrorProperty, initial, IntArrayBundleStateStrategy())
        }
    }
}

class IntArrayListBundleStateStrategy<TView> : BundleStateStrategy<ArrayList<Int>?, TView>(
    getValue = Bundle::getIntegerArrayList,
    setValue = Bundle::putIntegerArrayList
)

class BooleanBundleStateStrategy<TView> : BundleStateStrategy<Boolean, TView>(
    getValue = { key -> getBoolean(key) },
    setValue = { key, value -> putBoolean(key, value) }
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Boolean, TView>? = null,
            initial: Boolean,
        ): BundleStateProxyProvider<Boolean, TView> {
            return state(getMirrorProperty, initial, BooleanBundleStateStrategy())
        }
    }
}

class BooleanArrayBundleStateStrategy<TView> : BundleStateStrategy<BooleanArray?, TView>(
    getValue = { key -> getBooleanArray(key) },
    setValue = { key, value -> putBooleanArray(key, value) }
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<BooleanArray?, TView>? = null,
            initial: BooleanArray?,
        ): BundleStateProxyProvider<BooleanArray?, TView> {
            return state(getMirrorProperty, initial, BooleanArrayBundleStateStrategy())
        }
    }
}

class LongBundleStateStrategy<TView> : BundleStateStrategy<Long, TView>(
    getValue = Bundle::getLong,
    setValue = Bundle::putLong
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Long, TView>? = null,
            initial: Long,
        ): BundleStateProxyProvider<Long, TView> {
            return state(getMirrorProperty, initial, LongBundleStateStrategy())
        }
    }
}

class LongArrayBundleStateStrategy<TView> : BundleStateStrategy<LongArray?, TView>(
    getValue = Bundle::getLongArray,
    setValue = Bundle::putLongArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<LongArray?, TView>? = null,
            initial: LongArray?,
        ): BundleStateProxyProvider<LongArray?, TView> {
            return state(getMirrorProperty, initial, LongArrayBundleStateStrategy())
        }
    }
}

class ShortBundleStateStrategy<TView> : BundleStateStrategy<Short, TView>(
    getValue = Bundle::getShort,
    setValue = Bundle::putShort
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Short, TView>? = null,
            initial: Short,
        ): BundleStateProxyProvider<Short, TView> {
            return state(getMirrorProperty, initial, ShortBundleStateStrategy())
        }
    }
}

class ShortArrayBundleStateStrategy<TView> : BundleStateStrategy<ShortArray?, TView>(
    getValue = Bundle::getShortArray,
    setValue = Bundle::putShortArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<ShortArray?, TView>? = null,
            initial: ShortArray?,
        ): BundleStateProxyProvider<ShortArray?, TView> {
            return state(getMirrorProperty, initial, ShortArrayBundleStateStrategy())
        }
    }
}

class FloatBundleStateStrategy<TView> : BundleStateStrategy<Float, TView>(
    getValue = Bundle::getFloat,
    setValue = Bundle::putFloat
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Float, TView>? = null,
            initial: Float,
        ): BundleStateProxyProvider<Float, TView> {
            return state(getMirrorProperty, initial, FloatBundleStateStrategy())
        }
    }
}

class FloatArrayBundleStateStrategy<TView> : BundleStateStrategy<FloatArray?, TView>(
    getValue = Bundle::getFloatArray,
    setValue = Bundle::putFloatArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<FloatArray?, TView>? = null,
            initial: FloatArray?,
        ): BundleStateProxyProvider<FloatArray?, TView> {
            return state(getMirrorProperty, initial, FloatArrayBundleStateStrategy())
        }
    }
}

class DoubleBundleStateStrategy<TView> : BundleStateStrategy<Double, TView>(
    getValue = Bundle::getDouble,
    setValue = Bundle::putDouble
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Double, TView>? = null,
            initial: Double,
        ): BundleStateProxyProvider<Double, TView> {
            return state(getMirrorProperty, initial, DoubleBundleStateStrategy())
        }
    }
}

class DoubleArrayBundleStateStrategy<TView> : BundleStateStrategy<DoubleArray?, TView>(
    getValue = Bundle::getDoubleArray,
    setValue = Bundle::putDoubleArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<DoubleArray?, TView>? = null,
            initial: DoubleArray?,
        ): BundleStateProxyProvider<DoubleArray?, TView> {
            return state(getMirrorProperty, initial, DoubleArrayBundleStateStrategy())
        }
    }
}

class CharBundleStateStrategy<TView> : BundleStateStrategy<Char, TView>(
    getValue = Bundle::getChar,
    setValue = Bundle::putChar
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Char, TView>? = null,
            initial: Char,
        ): BundleStateProxyProvider<Char, TView> {
            return state(getMirrorProperty, initial, CharBundleStateStrategy())
        }
    }
}

class CharArrayBundleStateStrategy<TView> : BundleStateStrategy<CharArray?, TView>(
    getValue = Bundle::getCharArray,
    setValue = Bundle::putCharArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<CharArray?, TView>? = null,
            initial: CharArray?,
        ): BundleStateProxyProvider<CharArray?, TView> {
            return state(getMirrorProperty, initial, CharArrayBundleStateStrategy())
        }
    }
}

class ByteBundleStateStrategy<TView> : BundleStateStrategy<Byte, TView>(
    getValue = Bundle::getByte,
    setValue = Bundle::putByte
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<Byte, TView>? = null,
            initial: Byte,
        ): BundleStateProxyProvider<Byte, TView> {
            return state(getMirrorProperty, initial, ByteBundleStateStrategy())
        }
    }
}

class ByteArrayBundleStateStrategy<TView> : BundleStateStrategy<ByteArray?, TView>(
    getValue = Bundle::getByteArray,
    setValue = Bundle::putByteArray
) {
    interface Factory<TView> : BaseProxyFactory<TView> {

        fun state(
            getMirrorProperty: GetViewProperty<ByteArray?, TView>? = null,
            initial: ByteArray?,
        ): BundleStateProxyProvider<ByteArray?, TView> {
            return state(getMirrorProperty, initial, ByteArrayBundleStateStrategy())
        }
    }
}