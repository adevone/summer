package summer.events

import summer.GetViewProvider

/**
 * Proxy that executes event following rules defined in the [EventProxyStrategy].
 * Can execute events with 0-12 arguments.
 */
class EventProxy<out TView, in TOwner>(
    private val performViewEvent: (TView, params: Array<out Any?>) -> Unit,
    private val owner: TOwner,
    private val getViewProvider: GetViewProvider<TView>,
    private val listener: EventProxyListener<TView, TOwner>?,
    private val strategy: EventProxyStrategy<TView, TOwner>,
) {
    fun invoke(vararg params: Any?) {
        val execution = ViewEventExecution(
            performViewEvent,
            params,
            viewEventExecuted = { view, execution ->
                listener?.viewEventExecuted(view, strategy, execution, owner)
            }
        )
        strategy.proxyInvoked(execution, owner, getViewProvider)
        listener?.proxyInvoked(strategy, execution, owner)
    }

    fun viewCreated() {
        strategy.viewCreated(owner, getViewProvider)
    }
}
