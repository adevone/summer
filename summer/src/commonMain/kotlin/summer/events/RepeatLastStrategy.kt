package summer.events

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

    override fun viewCreated(view: TView) {
        lastApplication?.let { applyArgs ->
            val action = applyArgs(view)
            action()
        }
    }

    interface Factory<TView> : EventFactory<TView> {

        fun ActionProvider<TView, () -> Unit>.repeatLast() = build(
            strategy = RepeatLastStrategy(::view)
        )

        fun <T1> ActionProvider<TView, (T1) -> Unit>.repeatLast() = build(
            strategy = RepeatLastStrategy(::view)
        )

        fun <T1, T2> ActionProvider<TView, (T1, T2) -> Unit>.repeatLast() = build(
            strategy = RepeatLastStrategy(::view)
        )

        fun <T1, T2, T3> ActionProvider<TView, (T1, T2, T3) -> Unit>.repeatLast() = build(
            strategy = RepeatLastStrategy(::view)
        )
    }
}