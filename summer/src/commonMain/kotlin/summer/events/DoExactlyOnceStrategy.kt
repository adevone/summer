package summer.events

class DoExactlyOnceStrategy<TView>(
    private val getView: () -> TView?
) : SummerEventStrategy<TView> {

    private var notExecutedApplication: ApplyArgs<TView>? = null

    override fun called(applyArgs: ApplyArgs<TView>) {
        val view = getView()
        if (view != null) {
            val action = applyArgs(view)
            action()
        } else {
            notExecutedApplication = applyArgs
        }
    }

    override fun viewCreated(view: TView) {
        notExecutedApplication?.let { applyArgs ->
            val action = applyArgs(view)
            action()
        }
        notExecutedApplication = null
    }

    interface Factory<TView> : EventFactory<TView> {

        fun ActionProvider<TView, () -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1> ActionProvider<TView, (T1) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2> ActionProvider<TView, (T1, T2) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2, T3> ActionProvider<TView, (T1, T2, T3) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2, T3, T4> ActionProvider<TView, (T1, T2, T3, T4) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5> ActionProvider<TView, (T1, T2, T3, T4, T5) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6> ActionProvider<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy(::view)
        )
    }
}