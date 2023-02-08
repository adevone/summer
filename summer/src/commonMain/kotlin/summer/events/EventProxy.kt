package summer.events

import summer.ViewStateProvider
import summer.ViewProxyProvider

/**
 * Proxy that called on each event defined in [ViewProxyProvider] invocation.
 * Initiates performing of event provided by following rules defined in [strategy].
 * Arity of events is constrained by [EventPerformerFactory].
 */
class EventProxy<out TView, in TOwner>(
    private val performViewEvent: (TView, params: Array<out Any?>) -> Unit,
    private val owner: TOwner,
    private val viewStateProvider: ViewStateProvider<TView>,
    private val listener: EventProxyListener<TView, TOwner>?,
    private val strategy: EventProxyStrategy<TView, TOwner>,
) {
    fun invoke(vararg params: Any?) {
        val performance = EventPerformance(
            performViewEvent,
            params,
            viewEventExecuted = { view, execution ->
                listener?.viewEventExecuted(view, strategy, execution, owner)
            }
        )
        strategy.proxyInvoked(performance, owner, viewStateProvider)
        listener?.proxyInvoked(strategy, performance, owner)
    }

    fun viewCreated() {
        strategy.viewCreated(owner, viewStateProvider)
    }

    fun viewAppeared() {
        strategy.viewAppeared(owner, viewStateProvider)
    }
}
