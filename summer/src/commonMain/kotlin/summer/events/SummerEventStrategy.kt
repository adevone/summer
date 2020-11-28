package summer.events

import summer.LifecycleSummerViewModel
import summer.ViewProvider

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
interface SummerEventStrategy<out TView, in TOwner : ViewProvider<TView>> {
    /**
     * [SummerEvent] was invoked
     */
    fun called(owner: TOwner, applyArgs: ApplyArgs<TView>)

    /**
     * [LifecycleSummerViewModel.viewCreated] was called
     */
    fun viewCreated(owner: TOwner) {}
}


/**
 * Provider of view action with applied arguments.
 */
typealias ApplyArgs<TView> = (TView) -> (() -> Unit)