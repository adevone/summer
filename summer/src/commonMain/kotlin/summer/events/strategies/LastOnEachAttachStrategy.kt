package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventProxy
import summer.events.EventProxyStrategy
import summer.events.EventPerformance

/**
 * Performs event on each invoke of [EventProxy] if view exists and
 * repeats last performance on each view creation.
 */
class LastOnEachAttachStrategy<TView> : EventProxyStrategy<TView, Nothing?> {

    private var lastPerformance: EventPerformance<TView>? = null

    override fun proxyInvoked(
        performance: EventPerformance<TView>,
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            performance.performEvent(view)
        }
        lastPerformance = performance
    }

    override fun viewCreated(
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            lastPerformance?.performEvent(view)
        }
    }
}