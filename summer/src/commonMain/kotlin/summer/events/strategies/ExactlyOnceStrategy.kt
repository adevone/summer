package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy
import summer.events.EventPerformance
import summer.events.EventPerformer

/**
 * If view exists then event will be performed right now.
 * If view does not exist then event will be performed on view creation.
 */
class ExactlyOnceStrategy<TView> : EventProxyStrategy<TView, Nothing?> {

    private var notPerformed = mutableListOf<EventPerformance<TView>>()

    override fun proxyInvoked(
        performance: EventPerformance<TView>,
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            performance.performEvent(view)
        } else {
            notPerformed.add(performance)
        }
    }

    override fun viewCreated(
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
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