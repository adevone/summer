package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventProxy
import summer.events.EventProxyStrategy
import summer.events.ViewEventExecution

/**
 * Proxies each call of [EventProxy] to action if view exists and
 * repeats last call of [EventProxy] on each view creation.
 */
class RepeatLastStrategy<TView> : EventProxyStrategy<TView, Any?> {

    private var lastExecution: ViewEventExecution<TView, Any?>? = null

    override fun proxyInvoked(
        execution: ViewEventExecution<TView, Any?>,
        owner: Any?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            execution.execute(view)
        }
        lastExecution = execution
    }

    override fun viewCreated(
        owner: Any?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            lastExecution?.execute(view)
        }
    }
}