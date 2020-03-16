package summer.events

interface SummerEventStrategy<TView> {
    fun called(getAction: (TView) -> (() -> Unit))
    fun viewCreated() {}
    fun viewDestroyed() {}
}