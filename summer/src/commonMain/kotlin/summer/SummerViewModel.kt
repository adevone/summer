package summer

import summer.events.EventProxy
import summer.state.StateProxy

/**
 * Base viewModel. Helps with view state restoring (see [StateProxy])
 * and performing of events (see [EventProxy]).
 *
 * @param [TView] see [GetViewProvider]
 */
abstract class SummerViewModel<TView> :
    DefaultSummerViewModelImpl<TView>(),
    ViewProxyProvider<TView>

@Deprecated(
    message = "use SummerViewModel instead",
    replaceWith = ReplaceWith("SummerViewModel<TView>"),
    level = DeprecationLevel.ERROR
)
typealias SummerPresenter<TView> = SummerViewModel<TView>