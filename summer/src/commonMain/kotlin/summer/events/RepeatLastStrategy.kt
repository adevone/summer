package summer.events

import summer.GetViewProvider

/**
 * Proxies each call of [SummerEvent] to action if view exists and
 * repeats last call of [SummerEvent] on each view creation.
 */
class RepeatLastStrategy<TView> : SummerEventStrategy<TView, Any?> {

    private var lastExecuted: SummerEvent.ViewEventExecutor<TView>? = null

    override fun called(
        viewEventExecutor: SummerEvent.ViewEventExecutor<TView>,
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