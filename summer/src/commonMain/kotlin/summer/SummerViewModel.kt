package summer

import summer.events.EventProxy
import summer.state.StateProxy

/**
 * Base viewModel. Helps with view state restoring (see [StateProxy])
 * and executing of events (see [EventProxy]).
 *
 * @param [TView] see [GetViewProvider]
 */
abstract class SummerViewModel<TView> :
    DefaultSummerViewModelImpl<TView>(),
    ViewProxyProvider<TView>
