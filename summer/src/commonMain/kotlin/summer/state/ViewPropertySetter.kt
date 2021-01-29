package summer.state

import kotlin.jvm.JvmField
import kotlin.reflect.KMutableProperty0

class ViewPropertySetter<T, TView>(
    @JvmField val getViewProperty: GetViewProperty<T, TView>? = null,
    private val onViewPropertySet: (
        value: T,
        view: TView,
        viewProperty: KMutableProperty0<T>?
    ) -> Unit,
) {
    fun setIfExists(value: T, view: TView) {
        val viewProperty = getViewProperty?.invoke(view)
        viewProperty?.set(value)
        onViewPropertySet(value, view, viewProperty)
    }
}