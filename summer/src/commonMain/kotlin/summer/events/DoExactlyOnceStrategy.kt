package summer.events

import summer.ViewProvider

/**
 * If view exists then action will be executed right now.
 * If view does not exist then when view will be created.
 */
class DoExactlyOnceStrategy<TView> : SummerEventStrategy<TView, ViewProvider<TView>> {

    private var notExecutedApplication: ApplyArgs<TView>? = null

    override fun called(owner: ViewProvider<TView>, applyArgs: ApplyArgs<TView>) {
        val view = owner.getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        } else {
            notExecutedApplication = applyArgs
        }
    }

    override fun viewCreated(owner: ViewProvider<TView>) {
        val view = owner.getView()
        if (view != null) {
            notExecutedApplication?.let { applyArgs ->
                val action = applyArgs(view)
                action()
            }
            notExecutedApplication = null
        }
    }

    interface Factory<TView> : EventFactory<TView, ViewProvider<TView>> {

        fun ActionHolder<TView, () -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1> ActionHolder<TView, (T1) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2> ActionHolder<TView, (T1, T2) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3> ActionHolder<TView, (T1, T2, T3) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4> ActionHolder<TView, (T1, T2, T3, T4) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5> ActionHolder<TView, (T1, T2, T3, T4, T5) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6> ActionHolder<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )
    }
}