package summer.events

import summer.GetViewProvider

/**
 * If view exists then action will be executed right now.
 * If view does not exist then when view will be created.
 */
class DoExactlyOnceStrategy<TView> : SummerEventStrategy<TView, GetViewProvider<TView>> {

    private var notExecuted = mutableListOf<SummerEvent.ViewEventExecutor<TView>>()

    override fun called(
        owner: GetViewProvider<TView>,
        viewEventExecutor: SummerEvent.ViewEventExecutor<TView>
    ) {
        val view = owner.getView()
        if (view != null) {
            viewEventExecutor.execute(view)
        } else {
            notExecuted.add(viewEventExecutor)
        }
    }

    override fun viewCreated(owner: GetViewProvider<TView>) {
        val view = owner.getView()
        if (view != null) {
            notExecuted.forEach { executor ->
                executor.execute(view)
            }
            notExecuted.clear()
        }
    }

    interface Factory<TView> : EventFactory<TView, GetViewProvider<TView>> {

        fun EventBuilder<TView, () -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1> EventBuilder<TView, (T1) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2> EventBuilder<TView, (T1, T2) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3> EventBuilder<TView, (T1, T2, T3) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4> EventBuilder<TView, (T1, T2, T3, T4) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5> EventBuilder<TView, (T1, T2, T3, T4, T5) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6> EventBuilder<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )
    }
}