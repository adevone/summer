package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventProxy
import summer.events.EventProxyStrategy
import summer.events.ViewEventExecution

/**
 * Proxies each call of [EventProxy] to action if view exists and
 * repeats each call of [EventProxy] on each view creation.
 */
class RepeatAllStrategy<TView> : EventProxyStrategy<TView, Nothing?> {

    private val executions = mutableListOf<ViewEventExecution<TView>>()

    override fun proxyInvoked(
        execution: ViewEventExecution<TView>,
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            execution.execute(view)
        }
        executions.add(execution)
    }

    override fun viewCreated(
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            executions.forEach { executor ->
                executor.execute(view)
            }
        }
    }
}