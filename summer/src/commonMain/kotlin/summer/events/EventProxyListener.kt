package summer.events

/**
 * Allows to listen events of [EventProxy].
 *
 * Could be used to implement a time traveling.
 */
interface EventProxyListener<TView, TOwner> {
    /**
     * Always called before [viewEventExecuted] but also before [EventProxyStrategy.proxyInvoked]
     */
    fun proxyInvoked(
        strategy: EventProxyStrategy<TView, TOwner>,
        performance: EventPerformance<TView>,
        owner: TOwner,
    )

    /**
     * Always called after [proxyInvoked]
     */
    fun viewEventExecuted(
        view: TView,
        strategy: EventProxyStrategy<TView, TOwner>,
        performance: EventPerformance<TView>,
        owner: TOwner,
    )
}