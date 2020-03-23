package summer.events

import summer.ViewProvider

interface SummerEventStrategy<out TView, in TOwner : ViewProvider<TView>> {
    fun called(owner: TOwner, applyArgs: ApplyArgs<TView>)
    fun viewCreated(owner: TOwner) {}
}

typealias ApplyArgs<TView> = (TView) -> (() -> Unit)