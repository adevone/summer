package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventProxy
import summer.events.EventProxyStrategy
import summer.events.EventPerformance

/**
 * Performs event on each invoke of [EventProxy] if view exists and
 * repeats each performance on each view creation.
 */
class AllOnEachAttachStrategy<TView> : EventProxyStrategy<TView, Nothing?> {

    private val performances = mutableListOf<EventPerformance<TView>>()

    override fun proxyInvoked(
        performance: EventPerformance<TView>,
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            performance.performEvent(view)
        }
        performances.add(performance)
    }

    override fun viewCreated(
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            performances.forEach { executor ->
                executor.performEvent(view)
            }
        }
    }
}