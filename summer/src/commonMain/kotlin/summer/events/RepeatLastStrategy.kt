package summer.events

import summer.GetViewProvider

/**
 * Proxies each call of [EventProxy] to action if view exists and
 * repeats last call of [EventProxy] on each view creation.
 */
class RepeatLastStrategy<TView> : EventProxyStrategy<TView, Any?> {

    private var lastExecuted: EventProxy.ViewEventExecutor<TView>? = null

    override fun proxyInvoked(
        viewEventExecutor: EventProxy.ViewEventExecutor<TView>,
        owner: Any?,
        getViewProvider: GetViewProvider<TView>
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            viewEventExecutor.execute(view)
        }
        lastExecuted = viewEventExecutor
    }

    override fun viewCreated(
        owner: Any?,
        getViewProvider: GetViewProvider<TView>
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            lastExecuted?.execute(view)
        }
    }
}