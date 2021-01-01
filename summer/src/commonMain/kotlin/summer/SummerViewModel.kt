package summer

import summer.events.SummerEvent
import summer.state.SummerStateDelegate

/**
 * Base viewModel. Helps with view state restoring (see [SummerStateDelegate])
 * and executing of events (see [SummerEvent]).
 *
 * @param [TView] see [LifecycleViewModel]
 */
abstract class SummerViewModel<TView> :
    DefaultSummerViewModelImpl<TView>(),
    ViewProxyProvider<TView>
