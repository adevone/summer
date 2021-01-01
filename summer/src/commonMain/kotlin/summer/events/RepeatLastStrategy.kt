package summer.events

import summer.GetViewProvider

/**
 * Proxies each call of [SummerEvent] to action if view exists and
 * repeats last call of [SummerEvent] on each view creation.
 */
class RepeatLastStrategy<TView> : SummerEventStrategy<TView, GetViewProvider<TView>> {

    private var lastExecuted: SummerEvent.ViewEventExecutor<TView>? = null

    override fun called(
        owner: GetViewProvider<TView>,
        viewEventExecutor: SummerEvent.ViewEventExecutor<TView>
    ) {
        val view = owner.getView()
        if (view != null) {
            viewEventExecutor.execute(view)
        }
        lastExecuted = viewEventExecutor
    }

    override fun viewCreated(owner: GetViewProvider<TView>) {
        val view = owner.getView()
        if (view != null) {
            lastExecuted?.execute(view)
        }
    }
}