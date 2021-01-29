package summer.android

import android.os.Bundle
import dev.ahmedmourad.bundlizer.Bundlizer
import summer.LifecycleViewModel
import summer.strategy.SerializationStore

class SaveStateViewModelProvider<TView, TViewModel>(
    createViewModel: () -> TViewModel,
    view: TView,
) : SummerViewModelProvider<TView, TViewModel>(createViewModel, view)
        where TViewModel : LifecycleViewModel<TView>,
              TViewModel : SerializationStore.Holder {

    fun onSaveInstanceState(outState: Bundle) {
        val viewModel = requireViewModel()
        outState.apply {
            viewModel.serializationStore.dump().forEach { (key, valueWithSerializer) ->
                when (valueWithSerializer.value) {
                    // Scalars
                    is Boolean -> putBoolean(key, valueWithSerializer.value as Boolean)
                    is Byte -> putByte(key, valueWithSerializer.value as Byte)
                    is Char -> putChar(key, valueWithSerializer.value as Char)
                    is Double -> putDouble(key, valueWithSerializer.value as Double)
                    is Float -> putFloat(key, valueWithSerializer.value as Float)
                    is Int -> putInt(key, valueWithSerializer.value as Int)
                    is Long -> putLong(key, valueWithSerializer.value as Long)
                    is Short -> putShort(key, valueWithSerializer.value as Short)

                    // Scalar arrays
                    is BooleanArray -> putBooleanArray(key, valueWithSerializer.value as BooleanArray)
                    is ByteArray -> putByteArray(key, valueWithSerializer.value as ByteArray)
                    is CharArray -> putCharArray(key, valueWithSerializer.value as CharArray)
                    is DoubleArray -> putDoubleArray(key, valueWithSerializer.value as DoubleArray)
                    is FloatArray -> putFloatArray(key, valueWithSerializer.value as FloatArray)
                    is IntArray -> putIntArray(key, valueWithSerializer.value as IntArray)
                    is LongArray -> putLongArray(key, valueWithSerializer.value as LongArray)
                    is ShortArray -> putShortArray(key, valueWithSerializer.value as ShortArray)

                    //Complex types stores as Bundle
                    else -> putAll(Bundlizer.bundle(valueWithSerializer.serializer, valueWithSerializer.value))
                }
            }
        }
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        val viewModel = requireViewModel()
        if (savedInstanceState != null) {
            viewModel.serializationStore = SerializationStore().apply {
                viewModel.serializationStore.dump().forEach { (key, valueWithSerializer) ->
                    when (val savedValue = savedInstanceState[key]) {
                        // Scalars
                        is Boolean,
                        is Byte,
                        is Char,
                        is Double,
                        is Float,
                        is Int,
                        is Long,
                        is Short,

                            // Scalar arrays
                        is BooleanArray,
                        is ByteArray,
                        is CharArray,
                        is DoubleArray,
                        is FloatArray,
                        is IntArray,
                        is LongArray,
                        is ShortArray,
                        -> set(key, savedValue, valueWithSerializer.serializer)

                        is Bundle -> set(
                            key = key,
                            value = Bundlizer.unbundle(valueWithSerializer.serializer, savedValue),
                            serializer = valueWithSerializer.serializer
                        )
                    }
                }
            }
        }
    }
}
