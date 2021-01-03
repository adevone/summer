package summer.events

import summer.ProxyFactory

/**
 * DSL to create [ViewEventExecutor] and transform it
 * to [EventProxy] with user-defined [EventProxyStrategy].
 *
 * [TView] see [EventProxyStrategy]
 * [TOwner] see [EventProxyStrategy]
 */
interface EventProxyFactory<TView, TOwner> : ProxyFactory<TView> {

    fun eventProxyCreated(proxy: EventProxy<*, *>)

    fun <TFunction : Function<Unit>> event(getViewEvent: (TView) -> TFunction): EventProxyBuilder<TView, TFunction> {
        return EventProxyBuilder(getViewEvent)
    }

    @Suppress("UNCHECKED_CAST")
    fun <TFunction : Function<Unit>> EventProxyBuilder<TView, TFunction>.build(
        strategy: EventProxyStrategy<TView, TOwner>,
    ) = EventProxy(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    } as TFunction

    fun eventProxyOwner(): TOwner

    fun eventListener(): EventProxyListener<TView, TOwner>? = null
}

class EventProxyBuilder<TView, TFunction : Function<Unit>>(
    val getViewEvent: (TView) -> TFunction,
)