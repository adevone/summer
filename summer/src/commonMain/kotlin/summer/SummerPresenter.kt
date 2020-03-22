package summer

import summer.events.DoExactlyOnceStrategy
import summer.events.DoOnlyWhenAttachedStrategy
import summer.events.SummerEvent
import summer.events.SummerEventStrategy
import summer.state.InMemoryStateStrategy
import summer.state.InMemoryStore
import summer.state.InMemoryStoreProvider
import summer.state.StateDelegate

/**
 * Base presenter. Helps with view state restoring (see [StateDelegate])
 * and executing of events (see [SummerEvent]).
 */
abstract class SummerPresenter<TView> :
    BaseSummerPresenter<TView>,
    DoOnlyWhenAttachedStrategy.Factory<TView>,
    DoExactlyOnceStrategy.Factory<TView>,
    InMemoryStateStrategy.Factory<TView>,
    InMemoryStoreProvider {

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

    /**
     * Used for state storing by default. Can be overridden.
     */
    override val inMemoryStore = InMemoryStore()

    private val stateDelegates = mutableSetOf<StateDelegate<*, InMemoryStoreProvider>>()
    override fun stateDelegateCreated(delegate: StateDelegate<*, InMemoryStoreProvider>) {
        stateDelegates.add(delegate)
    }

    override fun getOwner() = this
}