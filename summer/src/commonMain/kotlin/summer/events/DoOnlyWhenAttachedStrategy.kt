package summer.events

import summer.ViewProvider

class DoOnlyWhenAttachedStrategy<TView> : SummerEventStrategy<TView, ViewProvider<TView>> {

    override fun called(owner: ViewProvider<TView>, applyArgs: (TView) -> (() -> Unit)) {
        val view = owner.getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        }
    }

    interface Factory<TView> : EventFactory<TView, ViewProvider<TView>> {

        fun ActionHolder<TView, () -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1> ActionHolder<TView, (T1) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2> ActionHolder<TView, (T1, T2) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3> ActionHolder<TView, (T1, T2, T3) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4> ActionHolder<TView, (T1, T2, T3, T4) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5> ActionHolder<TView, (T1, T2, T3, T4, T5) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6> ActionHolder<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )
    }
}