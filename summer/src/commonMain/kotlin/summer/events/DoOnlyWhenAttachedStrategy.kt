package summer.events

class DoOnlyWhenAttachedStrategy<TView>(
    private val getView: () -> TView?
) : SummerEventStrategy<TView> {

    override fun called(applyArgs: (TView) -> (() -> Unit)) {
        val view = getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        }
    }

    interface Factory<TView> : EventFactory<TView> {

        fun ActionProvider<TView, () -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1> ActionProvider<TView, (T1) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1, T2> ActionProvider<TView, (T1, T2) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1, T2, T3> ActionProvider<TView, (T1, T2, T3) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )
    }
}