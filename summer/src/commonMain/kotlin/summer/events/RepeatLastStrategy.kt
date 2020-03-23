package summer.events

import summer.ViewProvider

class RepeatLastStrategy<TView> : SummerEventStrategy<TView, ViewProvider<TView>> {

    private var lastApplication: ApplyArgs<TView>? = null

    override fun called(owner: ViewProvider<TView>, applyArgs: ApplyArgs<TView>) {
        val view = owner.getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        }
        lastApplication = applyArgs
    }

    override fun viewCreated(owner: ViewProvider<TView>) {
        val view = owner.getView()
        if (view != null) {
            lastApplication?.let { applyArgs ->
                val action = applyArgs(view)
                action()
            }
        }
    }
}