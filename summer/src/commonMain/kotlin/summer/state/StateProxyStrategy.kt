package summer.state

import summer.GetViewProvider
import summer.LifecycleViewModel
import summer.ViewLifecycleListener

/**
 * Determines how state will be stored.
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
        viewProperty: ViewProperty<T, TView, TOwner>,
        owner: TOwner,
        getViewProvider: GetViewProvider<TView>,
    ): T

    /**
     * Property delegated by [StateProxy] was set
     */
    fun setValue(
        value: T,
        viewProperty: ViewProperty<T, TView, TOwner>,
        owner: TOwner,
        getViewProvider: GetViewProvider<TView>,
    )

    /**
     * [ViewLifecycleListener.viewCreated] was called
     */
    fun viewCreated(
        viewProperty: ViewProperty<T, TView, TOwner>,
        owner: TOwner,
        getViewProvider: GetViewProvider<TView>,
    )
}