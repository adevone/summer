package summer.events

import summer.ViewProvider

typealias DefaultEventFactory<TView> = EventFactory<TView, ViewProvider<TView>>

interface EventFactory<TView, TOwner : ViewProvider<TView>> {

    fun eventStrategyCreated(strategy: SummerEventStrategy<TView>)

    fun <TFunction> event(which: (TView) -> TFunction): ActionProvider<TView, TOwner, TFunction> {
        return ActionProvider(viewProvider = getOwner(), getAction = which)
    }

    fun ActionProvider<TView, TOwner, () -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A0(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1> ActionProvider<TView, TOwner, (T1) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A1(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2> ActionProvider<TView, TOwner, (T1, T2) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A2(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3> ActionProvider<TView, TOwner, (T1, T2, T3) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A3(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4> ActionProvider<TView, TOwner, (T1, T2, T3, T4) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A4(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5> ActionProvider<TView, TOwner, (T1, T2, T3, T4, T5) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A5(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6> ActionProvider<TView, TOwner, (T1, T2, T3, T4, T5, T6) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A6(
        this.getAction,
        createStrategy(viewProvider)
    )

    fun <T1, T2, T3, T4, T5, T6, T7> ActionProvider<TView, TOwner, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A7(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6, T7, T8> ActionProvider<TView, TOwner, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A8(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> ActionProvider<TView, TOwner, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A9(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ActionProvider<TView, TOwner, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A10(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ActionProvider<TView, TOwner, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A11(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ActionProvider<TView, TOwner, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.build(
        createStrategy: TOwner.() -> SummerEventStrategy<TView>
    ) = SummerEvent.A12(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun getOwner(): TOwner
}

typealias DefaultActionProvider<TView, TFunction> = ActionProvider<TView, ViewProvider<TView>, TFunction>

class ActionProvider<TView, out TOwner : ViewProvider<TView>, TFunction>(
    val viewProvider: TOwner,
    val getAction: (TView) -> TFunction
)