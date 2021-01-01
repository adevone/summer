package summer.events

import summer.GetViewProvider

/**
 * Proxies each call of [SummerEvent] to action if view exists and
 * repeats each call of [SummerEvent] on each view creation.
 */
class RepeatAllStrategy<TView> : SummerEventStrategy<TView, Any?> {

    private val executors = mutableListOf<SummerEvent.ViewEventExecutor<TView>>()

    override fun called(
        viewEventExecutor: SummerEvent.ViewEventExecutor<TView>,
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