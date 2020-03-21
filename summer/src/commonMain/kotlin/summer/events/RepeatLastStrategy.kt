package summer.events

import summer.ViewProvider

class RepeatLastStrategy<TView>(
    private val getView: () -> TView?
) : SummerEventStrategy<TView> {

    private var lastApplication: ApplyArgs<TView>? = null

    override fun called(applyArgs: ApplyArgs<TView>) {
        val view = getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        }
        lastApplication = applyArgs
    }

    override fun viewCreated() {
        val view = getView()
        if (view != null) {
            lastApplication?.let { applyArgs ->
                val action = applyArgs(view)
                action()
            }
        }
    }
}