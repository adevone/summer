package summer.events.strategies

import summer.ViewStateProvider
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
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        val view = viewStateProvider.getView()
        if (view != null) {
            performance.performEvent(view)
        }
        lastPerformance = performance
    }

    override fun viewCreated(
        owner: Nothing?,
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        val view = viewStateProvider.getView()
        if (view != null) {
            lastPerformance?.performEvent(view)
        }
    }
}