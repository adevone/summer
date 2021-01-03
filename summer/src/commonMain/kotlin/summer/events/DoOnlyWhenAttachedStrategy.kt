package summer.events

import summer.GetViewProvider

/**
 * Action will be executed only if view exists.
 */
class DoOnlyWhenAttachedStrategy<TView> : EventProxyStrategy<TView, Any?> {

    override fun proxyInvoked(
        viewEventExecutor: EventProxy.ViewEventExecutor<TView>,
        owner: Any?,
        getViewProvider: GetViewProvider<TView>
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            viewEventExecutor.execute(view)
        }
    }

    interface ProxyFactory<TView> : EventProxyFactory<TView, Any?> {

        fun EventProxyBuilder<TView, () -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1> EventProxyBuilder<TView, (T1) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2> EventProxyBuilder<TView, (T1, T2) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3> EventProxyBuilder<TView, (T1, T2, T3) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4> EventProxyBuilder<TView, (T1, T2, T3, T4) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5> EventProxyBuilder<TView, (T1, T2, T3, T4, T5) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )

        fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.doOnlyWhenAttached() = build(
            strategy = DoOnlyWhenAttachedStrategy()
        )
    }
}