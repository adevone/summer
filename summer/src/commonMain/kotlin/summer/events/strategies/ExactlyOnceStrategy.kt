package summer.events.strategies

import summer.ViewStateProvider
import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy
import summer.events.EventPerformance
import summer.events.EventPerformer

/**
 * If view exists and appeared event will be performed right now.
 * If view does not exist and appeared event will be performed on view appearance.
 */
class ExactlyOnceStrategy<TView> : EventProxyStrategy<TView, Nothing?> {

    private var notPerformed = mutableListOf<EventPerformance<TView>>()

    override fun proxyInvoked(
        performance: EventPerformance<TView>,
        owner: Nothing?,
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        val view = viewStateProvider.getView()
        if (view != null && viewStateProvider.isViewAppeared) {
            performance.performEvent(view)
        } else {
            notPerformed.add(performance)
        }
    }

    override fun viewAppeared(
        owner: Nothing?,
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        val view = viewStateProvider.getView()
        if (view != null) {
            notPerformed.forEach { executor ->
                executor.performEvent(view)
            }
            notPerformed.clear()
        }
    }

    interface ProxyFactory<TView> : EventProxyFactory<TView> {

        fun <TEvent> EventPerformer<TView, TEvent>.exactlyOnce() = using(
            strategy = ExactlyOnceStrategy()
        )
    }
}