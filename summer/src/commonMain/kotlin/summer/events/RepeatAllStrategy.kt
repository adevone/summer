package summer.events

import summer.ViewProvider

class RepeatAllStrategy<TView> : SummerEventStrategy<TView, ViewProvider<TView>> {

    private val applications = mutableListOf<ApplyArgs<TView>>()

    override fun called(owner: ViewProvider<TView>, applyArgs: ApplyArgs<TView>) {
        val view = owner.getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        }
        applications.add(applyArgs)
    }

    override fun viewCreated(owner: ViewProvider<TView>) {
        val view = owner.getView()
        if (view != null) {
            applications.forEach { applyArgs ->
                val action = applyArgs(view)
                action()
            }
        }
    }
}