package summer.events

import summer.ViewProvider

interface EventFactory<TView> {

    fun eventCreated(event: SummerEvent<TView>)

    fun <TFunction> ViewProvider<TView>.event(which: (TView) -> TFunction): ActionProvider<TView, TFunction> {
        return ActionProvider(viewProvider = this, getAction = which)
    }

    fun ActionProvider<TView, () -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A0(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1> ActionProvider<TView, (T1) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A1(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2> ActionProvider<TView, (T1, T2) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A2(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3> ActionProvider<TView, (T1, T2, T3) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A3(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4> ActionProvider<TView, (T1, T2, T3, T4) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A4(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5> ActionProvider<TView, (T1, T2, T3, T4, T5) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A5(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6> ActionProvider<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A6(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A7(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A8(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A9(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A10(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A11(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ActionProvider<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.build(
        createStrategy: CreateStrategy<TView>
    ) = SummerEvent.A12(
        this.getAction,
        createStrategy { viewProvider.getView() }
    ).also { event ->
        eventCreated(event)
    }
}

typealias CreateStrategy<TView> = (() -> TView?).() -> SummerEventStrategy<TView>

class ActionProvider<TView, TFunction>(
    val viewProvider: ViewProvider<TView>,
    val getAction: (TView) -> TFunction
)