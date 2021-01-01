package summer

import summer.events.EventFactory
import summer.events.SummerEvent
import summer.state.StateFactory
import summer.state.SummerStateDelegate

/**
 * Extent from this class if you want to implement
 * custom [EventFactory] or [StateFactory]
 *
 * [TView] see [LifecycleSummerViewModel]
 */
abstract class RestoreSummerViewModel<TView, TStateOwner, TEventsOwner : GetViewProvider<TView>> :
    EnterLifecycleSummerViewModelImpl<TView>(),
    ViewProxyProvider<TView>,
    EventFactory<TView, TEventsOwner>,
    StateFactory<TView, TStateOwner> {

    override fun viewCreated() {
        // restore call placed there because viewModel methods may be called due view initialization.
        // restore must be called after initView
        stateDelegates.forEach { it.restore() }
        events.forEach { it.viewCreated() }

        super.viewCreated()
    }

    private val events = mutableListOf<SummerEvent<TView, TEventsOwner>>()
    override fun eventCreated(event: SummerEvent<TView, TEventsOwner>) {
        events.add(event)
    }

    private val stateDelegates = mutableSetOf<SummerStateDelegate<*, TStateOwner>>()
    override fun stateDelegateCreated(delegate: SummerStateDelegate<*, TStateOwner>) {
        stateDelegates.add(delegate)
    }
}