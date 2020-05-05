package summer.android

import summer.strategy.SaveStateStore
import summer.strategy.SaveStateStoreProvider
import android.os.Bundle
import dev.ahmedmourad.bundlizer.Bundlizer
import summer.LifecycleSummerPresenter

class SaveStatePresenterProvider<TView, TPresenter>(
    createPresenter: () -> TPresenter,
    view: TView
) : PresenterProvider<TView, TPresenter>(createPresenter, view)
    where TPresenter : LifecycleSummerPresenter<TView>, TPresenter : SaveStateStoreProvider {

    fun onSaveInstanceState(outState: Bundle) {
        val presenter = requirePresenter()
        outState.apply {
            presenter.saveStateStore.dump().forEach { (key, valueWithSerializer) ->
                when(valueWithSerializer.value) {
                    // Scalars
                    is Boolean -> putBoolean(key, valueWithSerializer.value)
                    is Byte -> putByte(key, valueWithSerializer.value)
                    is Char -> putChar(key, valueWithSerializer.value)
                    is Double -> putDouble(key, valueWithSerializer.value)
                    is Float -> putFloat(key, valueWithSerializer.value)
                    is Int -> putInt(key, valueWithSerializer.value)
                    is Long -> putLong(key, valueWithSerializer.value)
                    is Short -> putShort(key, valueWithSerializer.value)

                    // Scalar arrays
                    is BooleanArray -> putBooleanArray(key, valueWithSerializer.value)
                    is ByteArray -> putByteArray(key, valueWithSerializer.value)
                    is CharArray -> putCharArray(key, valueWithSerializer.value)
                    is DoubleArray -> putDoubleArray(key, valueWithSerializer.value)
                    is FloatArray -> putFloatArray(key, valueWithSerializer.value)
                    is IntArray -> putIntArray(key, valueWithSerializer.value)
                    is LongArray -> putLongArray(key, valueWithSerializer.value)
                    is ShortArray -> putShortArray(key, valueWithSerializer.value)

                    //Complex types stores as Bundle
                    else -> putAll(Bundlizer.bundle(valueWithSerializer.serializer, valueWithSerializer.value))
                }
            }
        }
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        val presenter = requirePresenter()
        if (savedInstanceState != null) {
            presenter.saveStateStore = SaveStateStore().apply {
                presenter.saveStateStore.dump().forEach { (key, valueWithSerializer) ->
                    when(val savedValue = savedInstanceState[key]) {
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
                        is ShortArray -> set(key, savedValue, valueWithSerializer.serializer)

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
