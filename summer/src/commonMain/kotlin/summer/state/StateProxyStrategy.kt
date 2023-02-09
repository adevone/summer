package summer.state

import summer.ViewStateProvider
import summer.LifecycleViewModel
import summer.ViewLifecycleListener
import kotlin.reflect.KProperty

/**
 * Determines behaviour of [StateProxy].
 *
 * [T] type of stored value.
 *
 * [TOwner]
 *   is a strategy dependencies container. If you want to pass some dependencies to
 *   strategy than define it on your custom interface and implement it
 *   on your [LifecycleViewModel].
 *   Store should be provided through [TOwner].
 */
interface StateProxyStrategy<T, TView, TOwner> {
    /**
     * Property delegated by [StateProxy] asked for a value
     */
    fun getValue(
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: TOwner,
        viewStateProvider: ViewStateProvider<TView>,
    ): T

    /**
     * Property delegated by [StateProxy] was set
     */
    fun setValue(
        value: T,
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: TOwner,
        viewStateProvider: ViewStateProvider<TView>,
    )

    /**
     * [ViewLifecycleListener.viewCreated] was called
     */
    fun viewCreated(
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: TOwner,
        viewStateProvider: ViewStateProvider<TView>,
    )
}