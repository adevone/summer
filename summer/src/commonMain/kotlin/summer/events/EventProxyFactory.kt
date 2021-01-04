package summer.events

import summer.ProxyFactory
import summer.ViewProxyProvider
import summer.events.strategies.DoExactlyOnceStrategy

/**
 * DSL to create [EventProxyBuilder] and transform it
 * to [EventProxy] with user-defined [EventProxyStrategy].
 *
 * [TView] see [EventProxyStrategy]
 */
interface EventProxyFactory<TView> : ProxyFactory<TView> {
    /**
     * First step of [EventProxy] creation see [ViewProxyProvider].
     */
    fun event(getViewEvent: (TView) -> Function<Unit>): EventProxyBuilder<TView> {
        return EventProxyBuilder(getViewEvent)
    }

    /**
     * Generic factory method to build [EventProxy] with any [EventProxyStrategy].
     *
     * Example of based on the example in [ViewProxyProvider] usage:
     * override val method = event { it.method }.build(DoExactlyOnceStrategy(), owner = null)
     *
     * Also can be used for custom [EventProxyFactory].
     * See an example in [DoExactlyOnceStrategy.ProxyFactory]
     *
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

    /**
     * Convenient [build] shorthand for [EventProxyStrategy] without owner.
     */
    fun EventProxyBuilder<TView>.build(
        strategy: EventProxyStrategy<TView, Nothing?>,
    ) = build(
        strategy = strategy,
        owner = null
    )

    fun eventProxyCreated(proxy: EventProxy<*, *>)
}

/**
 * Write an extension on this class in your [EventProxyFactory]
 * to create a shorthand for your [EventProxyStrategy] creation.
 */
class EventProxyBuilder<TView>(
    val getViewEvent: (TView) -> Function<Unit>,
)