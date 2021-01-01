package summer.events

import summer.GetViewProvider

/**
 * Proxies each call of [SummerEvent] to action if view exists and
 * repeats each call of [SummerEvent] on each view creation.
 */
class RepeatAllStrategy<TView> : SummerEventStrategy<TView, GetViewProvider<TView>> {

    private val executors = mutableListOf<SummerEvent.ViewEventExecutor<TView>>()

    override fun called(
        owner: GetViewProvider<TView>,
        viewEventExecutor: SummerEvent.ViewEventExecutor<TView>
    ) {
        val view = owner.getView()
        if (view != null) {
            viewEventExecutor.execute(view)
        }
        executors.add(viewEventExecutor)
    }

    override fun viewCreated(owner: GetViewProvider<TView>) {
        val view = owner.getView()
        if (view != null) {
            executors.forEach { executor ->
                executor.execute(view)
            }
        }
    }
}