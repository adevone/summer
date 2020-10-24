package summer.events

import summer.ViewProvider

/**
 * Action will be executed only if view exists.
 */
class DoOnlyWhenAttachedStrategy<TView> : SummerEventStrategy<TView, ViewProvider<TView>> {

    override fun called(owner: ViewProvider<TView>, applyArgs: (TView) -> (() -> Unit)) {
        val view = owner.getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        }
    }

    interface Factory<TView> : EventFactory<TView, ViewProvider<TView>> {

        fun EventBuilder<TView, () -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1> EventBuilder<TView, (T1) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2> EventBuilder<TView, (T1, T2) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3> EventBuilder<TView, (T1, T2, T3) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4> EventBuilder<TView, (T1, T2, T3, T4) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5> EventBuilder<TView, (T1, T2, T3, T4, T5) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6> EventBuilder<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )
    }
}