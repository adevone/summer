package summer.events

import summer.ViewProvider

class DoExactlyOnceStrategy<TView>(
    private val viewProvider: ViewProvider<TView>
) : SummerEventStrategy<TView> {

    private var notExecutedApplication: ApplyArgs<TView>? = null

    override fun called(applyArgs: ApplyArgs<TView>) {
        val view = viewProvider.getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        } else {
            notExecutedApplication = applyArgs
        }
    }

    override fun viewCreated() {
        val view = viewProvider.getView()
        if (view != null) {
            notExecutedApplication?.let { applyArgs ->
                val action = applyArgs(view)
                action()
            }
            notExecutedApplication = null
        }
    }

    interface Factory<TView> : DefaultEventFactory<TView> {

        fun DefaultActionProvider<TView, () -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1> DefaultActionProvider<TView, (T1) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2> DefaultActionProvider<TView, (T1, T2) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2, T3> DefaultActionProvider<TView, (T1, T2, T3) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2, T3, T4> DefaultActionProvider<TView, (T1, T2, T3, T4) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2, T3, T4, T5> DefaultActionProvider<TView, (T1, T2, T3, T4, T5) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2, T3, T4, T5, T6> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> DefaultActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.doExactlyOnce() = build(
            createStrategy = ::DoExactlyOnceStrategy
        )
    }
}