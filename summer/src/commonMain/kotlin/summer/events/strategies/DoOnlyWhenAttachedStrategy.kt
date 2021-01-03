package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventProxyBuilder
import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy
import summer.events.ViewEventExecution

/**
 * Action will be executed only if view exists.
 */
class DoOnlyWhenAttachedStrategy<TView> : EventProxyStrategy<TView, Nothing?> {

    override fun proxyInvoked(
        execution: ViewEventExecution<TView, Nothing?>,
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            execution.execute(view)
        }
    }

    interface ProxyFactory<TView> : EventProxyFactory<TView> {

        fun EventProxyBuilder<TView>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )
    }
}