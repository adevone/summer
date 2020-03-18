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

        fun <T1, T2, T3, T4> ActionProvider<TView, (T1, T2, T3, T4) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5> ActionProvider<TView, (T1, T2, T3, T4, T5) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6> ActionProvider<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy(::view)
        )
    }
}