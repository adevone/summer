package summer.events

import summer.ViewProvider

class DoOnlyWhenAttachedStrategy<TView>(
    private val viewProvider: ViewProvider<TView>
) : SummerEventStrategy<TView> {

    override fun called(applyArgs: (TView) -> (() -> Unit)) {
        val view = viewProvider.getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        }
    }

    interface Factory<TView> : DefaultEventFactory<TView> {

        fun DefaultActionProvider<TView, () -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1> DefaultActionProvider<TView, (T1) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2> DefaultActionProvider<TView, (T1, T2) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2, T3> DefaultActionProvider<TView, (T1, T2, T3) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2, T3, T4> DefaultActionProvider<TView, (T1, T2, T3, T4) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2, T3, T4, T5> DefaultActionProvider<TView, (T1, T2, T3, T4, T5) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2, T3, T4, T5, T6> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.doOnlyWhenAttached() = build(
            createStrategy = ::DoOnlyWhenAttachedStrategy
        )
    }
}