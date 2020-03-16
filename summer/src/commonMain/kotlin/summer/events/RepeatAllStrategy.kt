package summer.events

class RepeatAllStrategy<TView>(
    private val getView: () -> TView?
) : SummerEventStrategy<TView> {

    private val applications = mutableListOf<ApplyArgs<TView>>()

    override fun called(applyArgs: ApplyArgs<TView>) {
        val view = getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        }
        applications.add(applyArgs)
    }

    override fun viewCreated(view: TView) {
        applications.forEach { applyArgs ->
            val action = applyArgs(view)
            action()
        }
    }
}