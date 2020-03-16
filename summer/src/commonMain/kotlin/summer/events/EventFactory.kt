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
}

class ActionProvider<TView, TFunction>(
    val getAction: (TView) -> TFunction
)