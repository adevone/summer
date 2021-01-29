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
     * Generic factory method to create [EventProxy] with any [EventProxyStrategy].
     *
     * Example of based on the example in [ViewProxyProvider] usage:
     * override val method = event { it.method }.perform.using(ExactlyOnceStrategy(), owner = null)
     *
     * Also can be used for custom [EventProxyFactory].
     * See an example in [ExactlyOnceStrategy.ProxyFactory]
     *
     * [TOwner] see [EventProxyStrategy]
     */
    fun <TOwner, TEvent> EventPerformer<TView, TEvent>.using(
        strategy: EventProxyStrategy<TView, TOwner>,
        owner: TOwner,
        listener: EventProxyListener<TView, TOwner>? = null,
    ): TEvent {
        val proxy = EventProxy(
            this.performViewEvent,
            owner,
            getViewProvider(),
            listener,
            strategy
        )
        eventProxyCreated(proxy)
        return this.createInvokeProxyAdapter(proxy)
    }

    /**
     * Convenient [using] shorthand for [EventProxyStrategy] without owner.
     */
    fun <TEvent> EventPerformer<TView, TEvent>.using(
        strategy: EventProxyStrategy<TView, Nothing?>,
    ) = using(
        strategy = strategy,
        owner = null
    )

    @Deprecated(
        replaceWith = ReplaceWith("this.perform.exactlyOnce()"),
        message = "use .perform.exactlyOnce()",
        level = DeprecationLevel.ERROR
    )
    fun GetViewEventHolder<*, *>.doExactlyOnce(): Nothing {
        throw AssertionError("deprecated")
    }

    @Deprecated(
        replaceWith = ReplaceWith("this.perform.onlyWhenAttached()"),
        message = "use .perform.onlyWhenAttached()",
        level = DeprecationLevel.ERROR
    )
    fun GetViewEventHolder<*, *>.doOnlyWhenAttached(): Nothing {
        throw AssertionError("deprecated")
    }

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