package summer

import summer.events.EventProxy
import summer.events.EventProxyFactory
import summer.state.StateProxy
import summer.state.StateProxyFactory

/**
 * Extent from this class if you want to implement
 * custom [EventProxyFactory] or [StateProxyFactory]
 *
 * @param [TView] see [ViewStateProvider]
 */
abstract class RestoreViewModel<TView> :
    LifecycleViewModel<TView>,
    EventProxyFactory<TView>,
    StateProxyFactory<TView> {

    override var getView: () -> TView? = { null }
    override var isViewAppeared: Boolean = false

    /**
     * Do not override to listen lifecycle see [ViewLifecycleListener.viewCreated]
     */
    override fun viewCreated() {
        stateProxies.forEach { it.viewCreated() }
        eventProxies.forEach { it.viewCreated() }
    }

    override fun viewAppeared() {
        isViewAppeared = true
        eventProxies.forEach { it.viewAppeared() }
    }

    override fun viewDisappeared() {
        isViewAppeared = false
    }

    private val eventProxies = mutableListOf<EventProxy<*, *>>()
    override fun eventProxyCreated(proxy: EventProxy<*, *>) {
        eventProxies.add(proxy)
    }

    private val stateProxies = mutableSetOf<StateProxy<*, *, *>>()
    override fun stateProxyCreated(proxy: StateProxy<*, *, *>) {
        stateProxies.add(proxy)
    }

    override fun getViewProvider(): ViewStateProvider<TView> = this
}

@Deprecated(
    message = "use RestoreViewModel instead",
    replaceWith = ReplaceWith("RestoreViewModel<TView>"),
    level = DeprecationLevel.ERROR
)
typealias RestoreSummerPresenter<TView> = RestoreViewModel<TView>