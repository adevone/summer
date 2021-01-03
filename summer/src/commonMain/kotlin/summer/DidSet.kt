package summer

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class DidSet<T>(
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

class DidSetNotNull<T>(
    onSet: (T) -> Unit
) : DidSet<T?>(
    onSet = { value ->
        if (value != null) {
            onSet(value)
        }
    }
)

interface DidSetMixin {
    fun <T> didSet(onSet: (T) -> Unit) = DidSet(onSet)
    fun <T> didSetNotNull(onSet: (T) -> Unit) = DidSetNotNull(onSet)
}