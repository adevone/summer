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

    fun <TEvent> event(getViewEvent: (TView) -> TEvent): EventProxyBuilder<TView, TEvent> {
        return EventProxyBuilder(getViewEvent)
    }

    fun EventProxyBuilder<TView, () -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A0(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1> EventProxyBuilder<TView, (T1) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A1(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2> EventProxyBuilder<TView, (T1, T2) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A2(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2, T3> EventProxyBuilder<TView, (T1, T2, T3) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A3(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2, T3, T4> EventProxyBuilder<TView, (T1, T2, T3, T4) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A4(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2, T3, T4, T5> EventProxyBuilder<TView, (T1, T2, T3, T4, T5) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A5(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A6(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A7(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A8(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A9(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A10(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A11(
        this.getViewEvent,
        eventProxyOwner(),
        getViewProvider(),
        eventListener(),
        strategy
    ).also { event ->
        eventProxyCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> EventProxyBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.build(
        strategy: EventProxyStrategy<TView, TOwner>
    ) = EventProxy.A12(
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

class EventProxyBuilder<in TView, out TEvent>(
    val getViewEvent: (TView) -> TEvent
)