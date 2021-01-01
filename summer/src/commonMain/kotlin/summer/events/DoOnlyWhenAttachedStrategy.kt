package summer.events

import summer.GetViewProvider

/**
 * Action will be executed only if view exists.
 */
class DoOnlyWhenAttachedStrategy<TView> : SummerEventStrategy<TView, Any?> {

    override fun called(
        viewEventExecutor: SummerEvent.ViewEventExecutor<TView>,
        owner: Any?,
        getViewProvider: GetViewProvider<TView>
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            viewEventExecutor.execute(view)
        }
    }

    interface Factory<TView> : EventFactory<TView, Any?> {

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