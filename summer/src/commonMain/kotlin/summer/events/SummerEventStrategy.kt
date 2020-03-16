package summer.events

interface SummerEventStrategy<TView> {
    fun called(applyArgs: ApplyArgs<TView>)
    fun viewCreated(view: TView) {}
    fun viewDestroyed() {}
}

typealias ApplyArgs<TView> = (TView) -> (() -> Unit)