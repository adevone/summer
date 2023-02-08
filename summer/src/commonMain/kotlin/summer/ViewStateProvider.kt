package summer

import summer.state.StateProxy
import summer.events.EventProxy

/**
 * Provider of view to proxy [StateProxy] state changes and [EventProxy] invocations to it.
 */
interface ViewStateProvider<out TView> {
    val getView: () -> TView?
    val isViewAppeared: Boolean
}