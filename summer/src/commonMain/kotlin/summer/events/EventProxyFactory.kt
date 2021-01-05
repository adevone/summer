package summer.events

import summer.ProxyFactory
import summer.SummerViewModel
import summer.ViewProxyProvider
import summer.events.strategies.ExactlyOnceStrategy

/**
 * DSL to create [GetViewEventHolder] and transform it
 * to [EventProxy] with user-defined [EventProxyStrategy].
 *
 * [TView] see [EventProxyStrategy]
 */
interface EventProxyFactory<TView> : ProxyFactory<TView>, EventPerformerFactory {
    /**
     * Generic factory method to build [EventProxy] with any [EventProxyStrategy].
     *
     * Example of based on the example in [ViewProxyProvider] usage:
     * override val method = event { it.method }.perform.build(DoExactlyOnceStrategy(), owner = null)
     *
     * Also can be used for custom [EventProxyFactory].
     * See an example in [ExactlyOnceStrategy.ProxyFactory]
     *
     * [TOwner] see [EventProxyStrategy]
     */
    fun <TOwner, TEvent> EventPerformer<TView, TEvent>.build(
        strategy: EventProxyStrategy<TView, TOwner>,
        owner: TOwner,
        listener: EventProxyListener<TView, TOwner>? = null,
    ): TEvent {
        val proxy = EventProxy(
            performViewEvent,
            owner,
            getViewProvider(),
            listener,
            strategy
        ).also { event ->
            eventProxyCreated(event)
        }
        return this.createInvokeProxyAdapter(proxy)
    }

    /**
     * Convenient [build] shorthand for [EventProxyStrategy] without owner.
     */
    fun <TEvent> EventPerformer<TView, TEvent>.build(
        strategy: EventProxyStrategy<TView, Nothing?>,
    ) = build(
        strategy = strategy,
        owner = null
    )

    /**
     * First step of [EventProxy] creation. See [ViewProxyProvider].
     */
    fun <TEvent> event(getViewEvent: (TView) -> TEvent): GetViewEventHolder<TView, TEvent> {
        return GetViewEventHolder(getViewEvent)
    }

    fun eventProxyCreated(proxy: EventProxy<*, *>)
}

/**
 * Generic event holder. [EventProxyFactory] adapts it
 * to form convenient for [EventProxy] creation.
 */
class GetViewEventHolder<TView, TEvent>(
    val getViewEvent: (TView) -> TEvent,
)