package summer.example.ui.base

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingDelegate<TBinding : ViewBinding>(
    private val createBinding: (LayoutInflater) -> TBinding
) : ReadOnlyProperty<Any?, TBinding> {

    private var binding: ViewBinding? = null

    fun inflateBinding(inflater: LayoutInflater): TBinding {
        val binding = createBinding(inflater)
        this.binding = binding
        return binding
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): TBinding {
        @Suppress("UNCHECKED_CAST")
        return (binding as? TBinding) ?: throw ViewBindingNotProvidedException()
    }

    fun clearBinding() {
        binding = null
    }
}