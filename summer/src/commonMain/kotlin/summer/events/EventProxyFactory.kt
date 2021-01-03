package summer.events

import summer.ProxyFactory

/**
 * DSL to create [EventProxyBuilder] and transform it
 * to [EventProxy] with user-defined [EventProxyStrategy].
 *
 * [TView] see [EventProxyStrategy]
 */
interface EventProxyFactory<TView> : ProxyFactory<TView> {

    fun eventProxyCreated(proxy: EventProxy<*, *>)

    fun event(getViewEvent: (TView) -> Function<Unit>): EventProxyBuilder<TView> {
        return EventProxyBuilder(getViewEvent)
    }

    /**
     * Convenience factory to strategies without owner
     */
    fun EventProxyBuilder<TView>.build(
        strategy: EventProxyStrategy<TView, Nothing?>,
    ) = build(
        strategy = strategy,
        owner = null
    )

    /**
     * [TOwner] see [EventProxyStrategy]
     */
    fun <TOwner> EventProxyBuilder<TView>.build(
        strategy: EventProxyStrategy<TView, TOwner>,
        owner: TOwner,
        listener: EventProxyListener<TView, TOwner>? = null,
    ) = EventProxy(
        this.getViewEvent,
        owner,
        getViewProvider(),
        listener,
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }
}

class EventProxyBuilder<TView>(
    val getViewEvent: (TView) -> Function<Unit>,
)