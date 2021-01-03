package summer

import summer.events.EventProxyFactory
import summer.events.EventProxy
import summer.events.EventProxyStrategy
import summer.state.StateProxyFactory
import summer.state.StateProxy
import summer.state.StateProxyStrategy

/**
 * Extent from this class if you want to implement
 * custom [EventProxyFactory] or [StateProxyFactory]
 *
 * @param [TView] see [GetViewProvider]
 * @param [TStateOwner] see [StateProxyStrategy]
 * @param [TEventsOwner] see [EventProxyStrategy]
 */
abstract class RestoreViewModel<TView, TStateOwner, TEventsOwner> :
    LifecycleViewModel<TView>,
    EventProxyFactory<TView, TEventsOwner>,
    StateProxyFactory<TView, TStateOwner> {

    override var getView: () -> TView? = { null }

    /**
     * Do not override to listen lifecycle @see [ViewLifecycleListener.viewCreated]
     */
    override fun viewCreated() {
        stateProxies.forEach { it.restore() }
        eventProxies.forEach { it.viewCreated() }
    }

    private val eventProxies = mutableListOf<EventProxy<*, *>>()
    override fun eventProxyCreated(proxy: EventProxy<*, *>) {
        eventProxies.add(proxy)
    }

    private val stateProxies = mutableSetOf<StateProxy<*, *, *>>()
    override fun stateProxyCreated(proxy: StateProxy<*, *, *>) {
        stateProxies.add(proxy)
    }

    override fun getViewProvider(): GetViewProvider<TView> = this
}