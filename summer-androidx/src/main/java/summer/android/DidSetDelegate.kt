package summer.android

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class DidSetDelegate<T>(
    private val onSet: (T) -> Unit
) : ReadWriteProperty<Any?, T> {

    private var value: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        @Suppress("UNCHECKED_CAST")
        return value as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        onSet(value)
    }
}

interface DidSetMixin {

    fun <T> didSet(onSet: (T) -> Unit) = DidSetDelegate(onSet)

    fun <T> didSetNotNull(onSet: (T) -> Unit) = DidSetDelegate<T?> { value ->
        if (value != null) {
            onSet(value)
        }
    }
}