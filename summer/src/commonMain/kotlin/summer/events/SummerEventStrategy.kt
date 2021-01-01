package summer.events

import summer.GetViewProvider
import summer.ViewLifecycleListener

/**
 * Rule that determine behaviour of [SummerEvent] when it invoked.
 *
 * [TView] is parent view. If you want some strategy
 *         to be executed only on specific view, you can define it.
 *
 * [TOwner] is strategy dependencies container. If you want to pass some dependencies to
 *          strategy than define it on your custom interface, extend it from [ViewProvider]
 *          and implement on your [LifecycleViewModel].
 */
interface SummerEventStrategy<TView, in TOwner> {
    /**
     * [SummerEvent] was invoked
     */
    fun called(
        viewEventExecutor: SummerEvent.ViewEventExecutor<TView>,
        owner: TOwner,
        getViewProvider: GetViewProvider<TView>
    )

    /**
     * [ViewLifecycleListener.viewCreated] was called
     */
    fun viewCreated(
        owner: TOwner,
        getViewProvider: GetViewProvider<TView>
    ) {
        // do nothing by default
    }
}