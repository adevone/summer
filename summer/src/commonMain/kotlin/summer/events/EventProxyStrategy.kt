package summer.events

import summer.ViewStateProvider
import summer.LifecycleViewModel
import summer.ViewLifecycleListener

/**
 * Rule that determine behaviour of [EventProxy] when it invoked.
 *
 * [TView]
 *   is parent a view. If you want some strategy
 *   to be performed only on the specific view, you can pass it to [TView].
 *
 * [TOwner]
 *   is strategy dependencies container. If you want to pass some dependencies to
 *   strategy than define it on your custom interface, extend it from [ViewStateProvider]
 *   and implement on your [LifecycleViewModel].
 */
interface EventProxyStrategy<TView, TOwner> {
    /**
     * [EventProxy] was invoked
     */
    fun proxyInvoked(
        performance: EventPerformance<TView>,
        owner: TOwner,
        viewStateProvider: ViewStateProvider<TView>,
    )

    /**
     * [ViewLifecycleListener.viewCreated] was called
     */
    fun viewCreated(
        owner: TOwner,
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        // do nothing by default
    }

    /**
     * [ViewLifecycleListener.viewAppeared] was called
     */
    fun viewAppeared(
        owner: TOwner,
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        // do nothing by default
    }
}