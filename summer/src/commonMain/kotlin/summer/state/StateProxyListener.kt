package summer.state

import kotlin.reflect.KProperty

/**
 * Allows to listen events of [StateProxy].
 *
 * Could be used to implement a time traveling.
 */
interface StateProxyListener<TView, TOwner> {
    /**
     * Always called before [viewPropertySet] and after the value stored
     */
    fun proxySet(
        value: Any?,
        property: KProperty<*>,
        owner: TOwner,
        strategy: StateProxyStrategy<*, TOwner>,
    )

    /**
     * Always called after [proxySet]
     */
    fun viewPropertySet(
        value: Any?,
        property: KProperty<*>,
        view: TView,
        owner: TOwner,
        strategy: StateProxyStrategy<*, TOwner>,
    )
}