package summer.events

import summer.ProxyFactory

/**
 * DSL to create [EventProxyBuilder] and transform it
 * to [EventProxy] with user-defined [EventProxyStrategy].
 *
 * [TView] see [EventProxyStrategy]
 * [TOwner] see [EventProxyStrategy]
 */
interface EventProxyFactory<TView, TOwner> : ProxyFactory<TView> {

    fun eventProxyCreated(proxy: EventProxy<*, *>)

    fun event(getViewEvent: (TView) -> Function<Unit>): EventProxyBuilder<TView> {
        return EventProxyBuilder(getViewEvent)
    }

    fun EventProxyBuilder<TView>.build(
        strategy: EventProxyStrategy<TView, TOwner>,
    ) = EventProxy(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun eventProxyOwner(): TOwner

    fun eventListener(): EventProxyListener<TView, TOwner>? = null
}

class EventProxyBuilder<TView>(
    val getViewEvent: (TView) -> Function<Unit>,
)