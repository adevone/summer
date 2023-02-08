package summer.events.strategies

import summer.ViewStateProvider
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
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        val view = viewStateProvider.getView()
        if (view != null) {
            performance.performEvent(view)
        }
        performances.add(performance)
    }

    override fun viewCreated(
        owner: Nothing?,
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        val view = viewStateProvider.getView()
        if (view != null) {
            performances.forEach { executor ->
                executor.performEvent(view)
            }
        }
    }
}