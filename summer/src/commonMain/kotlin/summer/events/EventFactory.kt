package summer.events

import summer.GetViewProvider

/**
 * DSL to create [EventBuilder] and transform it
 * to [SummerEvent] with user-defined [SummerEventStrategy].
 *
 * [TView] see [SummerEventStrategy]
 * [TOwner] see [SummerEventStrategy]
 */
interface EventFactory<TView, TOwner : GetViewProvider<TView>> {

    fun eventCreated(event: SummerEvent<*, *>)

    fun <TFunction> event(which: (TView) -> TFunction): EventBuilder<TView, TFunction> {
        return EventBuilder(getAction = which)
    }

    fun EventBuilder<TView, () -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A0(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1> EventBuilder<TView, (T1) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A1(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2> EventBuilder<TView, (T1, T2) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A2(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3> EventBuilder<TView, (T1, T2, T3) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A3(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4> EventBuilder<TView, (T1, T2, T3, T4) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A4(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5> EventBuilder<TView, (T1, T2, T3, T4, T5) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A5(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6> EventBuilder<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A6(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A7(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A8(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A9(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A10(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A11(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> EventBuilder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.build(
        strategy: SummerEventStrategy<TView, TOwner>
    ) = SummerEvent.A12(
        this.getAction,
        getEventsOwner(),
        getEventListener(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun getEventsOwner(): TOwner

    fun getEventListener(): EventListener<TView, TOwner>? = null
}

class EventBuilder<in TView, out TFunction>(
    val getAction: (TView) -> TFunction
)