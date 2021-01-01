package summer.events

import summer.GetViewProvider

/**
 * Proxies each call of [SummerEvent] to action if view exists and
 * repeats last call of [SummerEvent] on each view creation.
 */
class RepeatLastStrategy<TView> : SummerEventStrategy<TView, GetViewProvider<TView>> {

    private var lastApplication: ApplyArgs<TView>? = null

    override fun called(owner: GetViewProvider<TView>, applyArgs: ApplyArgs<TView>) {
        val view = owner.getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        }
        lastApplication = applyArgs
    }

    override fun viewCreated(owner: GetViewProvider<TView>) {
        val view = owner.getView()
        if (view != null) {
            lastApplication?.let { applyArgs ->
                val action = applyArgs(view)
                action()
            }
        }
    }
}