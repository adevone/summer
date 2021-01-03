package summer.events

import summer.GetViewProvider

/**
 * Proxies each call of [EventProxy] to action if view exists and
 * repeats each call of [EventProxy] on each view creation.
 */
class RepeatAllStrategy<TView> : EventProxyStrategy<TView, Any?> {

    private val executors = mutableListOf<EventProxy.ViewEventExecutor<TView>>()

    override fun proxyInvoked(
        viewEventExecutor: EventProxy.ViewEventExecutor<TView>,
        owner: Any?,
        getViewProvider: GetViewProvider<TView>
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            viewEventExecutor.execute(view)
        }
        executors.add(viewEventExecutor)
    }

    override fun viewCreated(
        owner: Any?,
        getViewProvider: GetViewProvider<TView>
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            executors.forEach { executor ->
                executor.execute(view)
            }
        }
    }
}