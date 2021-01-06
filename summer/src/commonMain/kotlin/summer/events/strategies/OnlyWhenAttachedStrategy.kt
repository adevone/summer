package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventPerformer
import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy
import summer.events.EventPerformance

/**
 * Event will be performed only if view exists.
 */
class OnlyWhenAttachedStrategy<TView> : EventProxyStrategy<TView, Nothing?> {

    override fun proxyInvoked(
        performance: EventPerformance<TView>,
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            performance.performEvent(view)
        }
    }

    interface ProxyFactory<TView> : EventProxyFactory<TView> {

        fun <TEvent> EventPerformer<TView, TEvent>.onlyWhenAttached() = using(
            strategy = OnlyWhenAttachedStrategy()
        )
    }
}