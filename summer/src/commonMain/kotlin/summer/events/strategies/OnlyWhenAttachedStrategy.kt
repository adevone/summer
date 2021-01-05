package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventPerformer
import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy
import summer.events.ViewEventExecution

/**
 * Action will be executed only if view exists.
 */
class OnlyWhenAttachedStrategy<TView> : EventProxyStrategy<TView, Nothing?> {

    override fun proxyInvoked(
        execution: ViewEventExecution<TView>,
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            execution.execute(view)
        }
    }

    interface ProxyFactory<TView> : EventProxyFactory<TView> {

        fun <TEvent> EventPerformer<TView, TEvent>.onlyWhenAttached() = build(
            strategy = OnlyWhenAttachedStrategy()
        )
    }
}