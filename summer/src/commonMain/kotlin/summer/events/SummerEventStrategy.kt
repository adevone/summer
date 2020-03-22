package summer.events

interface SummerEventStrategy<out TView> {
    fun called(applyArgs: ApplyArgs<TView>)
    fun viewCreated() {}
}

typealias ApplyArgs<TView> = (TView) -> (() -> Unit)