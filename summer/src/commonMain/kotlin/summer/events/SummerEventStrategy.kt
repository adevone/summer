package summer.events

import summer.ViewProvider

interface SummerEventStrategy<TView> {
    fun called(applyArgs: ApplyArgs<TView>)
    fun viewCreated() {}

    abstract class Factory<TStrategy : SummerEventStrategy<*>> : () -> TStrategy {
        abstract fun <TView> createStrategy(view: TView, provider: ViewProvider<TView>): TStrategy
    }
}

typealias ApplyArgs<TView> = (TView) -> (() -> Unit)