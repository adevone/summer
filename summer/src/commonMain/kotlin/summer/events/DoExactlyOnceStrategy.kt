package summer.events

import summer.GetViewProvider

/**
 * If view exists then action will be executed right now.
 * If view does not exist then when view will be created.
 */
class DoExactlyOnceStrategy<TView> : EventProxyStrategy<TView, Any?> {

    private var notExecuted = mutableListOf<EventProxy.ViewEventExecutor<TView>>()

    override fun proxyInvoked(
        viewEventExecutor: EventProxy.ViewEventExecutor<TView>,
        owner: Any?,
        getViewProvider: GetViewProvider<TView>
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            viewEventExecutor.execute(view)
        } else {
            notExecuted.add(viewEventExecutor)
        }
    }

    override fun viewCreated(
        owner: Any?,
        getViewProvider: GetViewProvider<TView>
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            notExecuted.forEach { executor ->
                executor.execute(view)
            }
            notExecuted.clear()
        }
    }

    interface ProxyFactory<TView> : EventProxyFactory<TView, Any?> {

        fun EventProxyBuilder<TView, () -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1> EventProxyBuilder<TView, (T1) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2> EventProxyBuilder<TView, (T1, T2) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3> EventProxyBuilder<TView, (T1, T2, T3) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4> EventProxyBuilder<TView, (T1, T2, T3, T4) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5> EventProxyBuilder<TView, (T1, T2, T3, T4, T5) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.doExactlyOnce() = build(
            strategy = DoExactlyOnceStrategy()
        )
    }
}