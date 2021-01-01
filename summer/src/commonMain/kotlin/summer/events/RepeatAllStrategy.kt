package summer.events

import summer.GetViewProvider

/**
 * Proxies each call of [SummerEvent] to action if view exists and
 * repeats each call of [SummerEvent] on each view creation.
 */
class RepeatAllStrategy<TView> : SummerEventStrategy<TView, GetViewProvider<TView>> {

    private val applications = mutableListOf<ApplyArgs<TView>>()

    override fun called(owner: GetViewProvider<TView>, applyArgs: ApplyArgs<TView>) {
        val view = owner.getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        }
        applications.add(applyArgs)
    }

    override fun viewCreated(owner: GetViewProvider<TView>) {
        val view = owner.getView()
        if (view != null) {
            applications.forEach { applyArgs ->
                val action = applyArgs(view)
                action()
            }
        }
    }
}