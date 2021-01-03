package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventProxyBuilder
import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy
import summer.events.ViewEventExecution

/**
 * If view exists then action will be executed right now.
 * If view does not exist then when view will be created.
 */
class DoExactlyOnceStrategy<TView> : EventProxyStrategy<TView, Any?> {

    private var notExecuted = mutableListOf<ViewEventExecution<TView, Any?>>()

    override fun proxyInvoked(
        execution: ViewEventExecution<TView, Any?>,
        owner: Any?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            execution.execute(view)
        } else {
            notExecuted.add(execution)
        }
    }

    override fun viewCreated(
        owner: Any?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            notExecuted.forEach { executor ->
                executor.execute(view)
            }
            notExecuted.clear()
        }
    }

    interface ProxyFactory<TView> : EventProxyFactory<TView, Any?> {

        fun <TFunction : Function<Unit>> EventProxyBuilder<TView, TFunction>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )
    }
}