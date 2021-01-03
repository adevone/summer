package summer.state

import kotlin.jvm.JvmField
import kotlin.reflect.KProperty

class ViewProperty<T, TView, TOwner>(
    @JvmField val initial: T,
    @JvmField val proxyProperty: KProperty<*>,
    private val getViewProperty: GetViewProperty<T, TView>? = null,
    private val owner: TOwner,
    private val listener: StateProxyListener<TView, TOwner>?,
    private val strategy: StateProxyStrategy<T, TView, TOwner>,
) {
    fun setIfExists(value: T, view: TView) {
        val viewProperty = getViewProperty?.invoke(view)
        viewProperty?.set(value)
        listener?.viewPropertySet(value, proxyProperty, viewProperty, view, owner, strategy)
    }
}