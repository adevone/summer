package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventProxyBuilder
import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy
import summer.events.ViewEventExecution

/**
 * Action will be executed only if view exists.
 */
class DoOnlyWhenAttachedStrategy<TView> : EventProxyStrategy<TView, Any?> {

    override fun proxyInvoked(
        execution: ViewEventExecution<TView, Any?>,
        owner: Any?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            execution.execute(view)
        }
    }

    interface ProxyFactory<TView> : EventProxyFactory<TView, Any?> {

        fun EventProxyBuilder<TView>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )
    }
}