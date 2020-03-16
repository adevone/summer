package summer.events

interface EventFactory<TView> {

    val view: TView?

    fun eventCreated(event: SummerEvent<TView>)

    fun <TFunction> event(which: (TView) -> TFunction): ActionProvider<TView, TFunction> {
        return ActionProvider(getAction = which)
    }

    fun ActionProvider<TView, () -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ): SummerEvent.A0<TView> = SummerEvent.A0(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1> ActionProvider<TView, (T1) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A1(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2> ActionProvider<TView, (T1, T2) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A2(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3> ActionProvider<TView, (T1, T2, T3) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A3(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4> ActionProvider<TView, (T1, T2, T3, T4) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A4(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5> ActionProvider<TView, (T1, T2, T3, T4, T5) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A5(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6> ActionProvider<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A6(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A7(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A8(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A9(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A10(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A11(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.build(
        strategy: SummerEventStrategy<TView>
    ) = SummerEvent.A12(
        this.getAction,
        strategy
    ).also { event ->
        eventCreated(event)
    }
}

class ActionProvider<TView, TFunction>(
    val getAction: (TView) -> TFunction
)