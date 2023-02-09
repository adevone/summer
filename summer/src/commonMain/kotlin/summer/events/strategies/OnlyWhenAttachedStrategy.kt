package summer.events.strategies

import summer.ViewStateProvider
import summer.events.EventPerformance
import summer.events.EventPerformer
import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy

/**
 * Event will be performed only if view exists and appeared.
 */
class OnlyWhenAttachedStrategy<TView> : EventProxyStrategy<TView, Nothing?> {

    override fun proxyInvoked(
        performance: EventPerformance<TView>,
        owner: Nothing?,
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        val view = viewStateProvider.getView()
        if (view != null && viewStateProvider.isViewAppeared) {
            performance.performEvent(view)
        }
    }

    interface ProxyFactory<TView> : EventProxyFactory<TView> {

        fun <TEvent> EventPerformer<TView, TEvent>.onlyWhenAttached() = using(
            strategy = OnlyWhenAttachedStrategy()
        )
    }
}