package summer

import summer.events.EventFactory
import summer.events.SummerEventStrategy
import summer.state.StateDelegate
import summer.state.StateFactory

abstract class RestoreSummerPresenter<TView, TStateOwner, TEventsOwner : ViewProvider<TView>> :
    LifecycleSummerPresenter<TView>,
    EventFactory<TView, TEventsOwner>,
    StateFactory<TView, TStateOwner> {

    override var getView: () -> TView? = { null }

    private var viewCreatedWasCalled = false

    override fun viewCreated() {
        // restore call placed there because presenter methods may be called due view initialization.
        // restore must be called after initView
        stateDelegates.forEach { it.restore() }
        eventStrategies.forEach { it.viewCreated() }

        if (!viewCreatedWasCalled) {
            onEnter()
        }
        viewCreatedWasCalled = true
    }

    /**
     * Called when [viewCreated] was called first times.
     */
    open fun onEnter() {}

    private val eventStrategies = mutableListOf<SummerEventStrategy<TView>>()
    override fun eventStrategyCreated(strategy: SummerEventStrategy<TView>) {
        eventStrategies.add(strategy)
    }

    private val stateDelegates = mutableSetOf<StateDelegate<*, TStateOwner>>()
    override fun stateDelegateCreated(delegate: StateDelegate<*, TStateOwner>) {
        stateDelegates.add(delegate)
    }
}