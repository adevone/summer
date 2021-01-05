package summer

import summer.events.EventPerformerFactory
import summer.events.EventProxy
import summer.events.EventProxyFactory
import summer.state.StateProxy
import summer.state.StateProxyFactory

/**
 * Extent from this class if you want to implement
 * custom [EventProxyFactory] or [StateProxyFactory]
 *
 * @param [TView] see [GetViewProvider]
 */
abstract class RestoreViewModel<TView> :
    LifecycleViewModel<TView>,
    EventProxyFactory<TView>,
    StateProxyFactory<TView> {

    override var getView: () -> TView? = { null }

    /**
     * Do not override to listen lifecycle see [ViewLifecycleListener.viewCreated]
     */
    override fun viewCreated() {
        stateProxies.forEach { it.viewCreated() }
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