package summer.events

import summer.ViewProvider

/**
 * DSL to create [ActionHolder] and transform it
 * to [SummerEvent] with user-defined [SummerEventStrategy].
 *
 * [TView] see [SummerEventStrategy]
 * [TOwner] see [SummerEventStrategy]
 */
interface EventFactory<TView, TOwner : ViewProvider<TView>> {

    fun eventCreated(event: SummerEvent<TView, TOwner>)

    fun <TFunction> event(which: (TView) -> TFunction): ActionHolder<TView, TFunction> {
        return ActionHolder(getAction = which)
    }

    fun ActionHolder<TView, () -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A0(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1> ActionHolder<TView, (T1) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A1(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2> ActionHolder<TView, (T1, T2) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A2(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3> ActionHolder<TView, (T1, T2, T3) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A3(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4> ActionHolder<TView, (T1, T2, T3, T4) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A4(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5> ActionHolder<TView, (T1, T2, T3, T4, T5) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A5(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6> ActionHolder<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A6(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A7(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A8(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A9(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A10(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A11(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ActionHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.build(
        strategy: SummerEventStrategy<TView, ViewProvider<TView>>
    ) = SummerEvent.A12(
        this.getAction,
        getEventsOwner(),
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun getEventsOwner(): ViewProvider<TView>
}

inline class ActionHolder<in TView, out TFunction>(
    val getAction: (TView) -> TFunction
)