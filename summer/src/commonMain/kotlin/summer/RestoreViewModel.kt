package summer

import summer.events.EventFactory
import summer.events.SummerEvent
import summer.events.SummerEventStrategy
import summer.state.StateFactory
import summer.state.SummerStateDelegate
import summer.state.SummerStateStrategy

/**
 * Extent from this class if you want to implement
 * custom [EventFactory] or [StateFactory]
 *
 * @param [TView] see [GetViewProvider]
 * @param [TStateOwner] see [SummerStateStrategy]
 * @param [TEventsOwner] see [SummerEventStrategy]
 */
abstract class RestoreViewModel<TView, TStateOwner, TEventsOwner> :
    EnterLifecycleSummerViewModelImpl<TView>(),
    EventFactory<TView, TEventsOwner>,
    StateFactory<TView, TStateOwner> {

    override fun viewCreated() {
        stateDelegates.forEach { it.restore() }
        events.forEach { it.viewCreated() }

        // onEnter must be called after restoring of the state
        // because the view must be fully initialized in onEnter
        super.viewCreated()
    }

    private val events = mutableListOf<SummerEvent<*, *>>()
    override fun eventCreated(event: SummerEvent<*, *>) {
        events.add(event)
    }

    private val stateDelegates = mutableSetOf<SummerStateDelegate<*, *, *>>()
    override fun stateDelegateCreated(delegate: SummerStateDelegate<*, *, *>) {
        stateDelegates.add(delegate)
    }

    override fun getViewProvider(): GetViewProvider<TView> = this
}