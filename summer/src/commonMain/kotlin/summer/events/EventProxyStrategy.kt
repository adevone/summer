package summer.events

import summer.GetViewProvider
import summer.LifecycleViewModel
import summer.ViewLifecycleListener

/**
 * Rule that determine behaviour of [EventProxy] when it invoked.
 *
 * [TView]
 *   is parent a view. If you want some strategy
 *   to be executed only on the specific view, you can pass it to [TView].
 *
 * [TOwner]
 *   is strategy dependencies container. If you want to pass some dependencies to
 *   strategy than define it on your custom interface, extend it from [GetViewProvider]
 *   and implement on your [LifecycleViewModel].
 */
interface EventProxyStrategy<TView, TOwner> {
    /**
     * [EventProxy] was invoked
     */
    fun proxyInvoked(
        execution: ViewEventExecution<TView, TOwner>,
        owner: TOwner,
        getViewProvider: GetViewProvider<TView>,
    )

    /**
     * [ViewLifecycleListener.viewCreated] was called
     */
    fun viewCreated(
        owner: TOwner,
        getViewProvider: GetViewProvider<TView>,
    ) {
        // do nothing by default
    }
}