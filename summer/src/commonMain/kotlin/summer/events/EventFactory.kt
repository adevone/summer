package summer.events

import summer.ViewProvider

typealias DefaultEventFactory<TView> = EventFactory<TView, ViewProvider<TView>, SummerEventStrategy<TView>>

interface EventFactory<TView, TViewProvider : ViewProvider<TView>, TStrategy : SummerEventStrategy<TView>> {

    fun eventStrategyCreated(strategy: TStrategy)

    fun <TFunction> TViewProvider.event(which: (TView) -> TFunction): ActionProvider<TView, TViewProvider, TFunction> {
        return ActionProvider(viewProvider = this, getAction = which)
    }

    fun ActionProvider<TView, TViewProvider, () -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A0(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1> ActionProvider<TView, TViewProvider, (T1) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A1(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2> ActionProvider<TView, TViewProvider, (T1, T2) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A2(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3> ActionProvider<TView, TViewProvider, (T1, T2, T3) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A3(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4> ActionProvider<TView, TViewProvider, (T1, T2, T3, T4) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A4(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5> ActionProvider<TView, TViewProvider, (T1, T2, T3, T4, T5) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A5(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6> ActionProvider<TView, TViewProvider, (T1, T2, T3, T4, T5, T6) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A6(
        this.getAction,
        createStrategy(viewProvider)
    )

    fun <T1, T2, T3, T4, T5, T6, T7> ActionProvider<TView, TViewProvider, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A7(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6, T7, T8> ActionProvider<TView, TViewProvider, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A8(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> ActionProvider<TView, TViewProvider, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A9(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ActionProvider<TView, TViewProvider, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A10(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ActionProvider<TView, TViewProvider, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A11(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ActionProvider<TView, TViewProvider, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.build(
        createStrategy: TViewProvider.() -> TStrategy
    ) = SummerEvent.A12(
        this.getAction,
        createStrategy(viewProvider).also { strategy ->
            eventStrategyCreated(strategy)
        }
    )
}

typealias DefaultActionProvider<TView, TFunction> = ActionProvider<TView, ViewProvider<TView>, TFunction>

class ActionProvider<TView, out TViewProvider : ViewProvider<TView>, TFunction>(
    val viewProvider: TViewProvider,
    val getAction: (TView) -> TFunction
)