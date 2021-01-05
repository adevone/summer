package summer.events.strategies

import summer.GetViewProvider
import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy
import summer.events.ViewEventExecution
import summer.events.EventPerformer

/**
 * If view exists then action will be executed right now.
 * If view does not exist then when view will be created.
 */
class ExactlyOnceStrategy<TView> : EventProxyStrategy<TView, Nothing?> {

    private var notExecuted = mutableListOf<ViewEventExecution<TView>>()

    override fun proxyInvoked(
        execution: ViewEventExecution<TView>,
        owner: Nothing?,
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
        owner: Nothing?,
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

    interface ProxyFactory<TView> : EventProxyFactory<TView> {

        fun <TEvent> EventPerformer<TView, TEvent>.exactlyOnce() = build(
            strategy = ExactlyOnceStrategy()
        )
    }
}