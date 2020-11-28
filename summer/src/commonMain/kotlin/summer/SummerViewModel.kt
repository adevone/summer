package summer

import summer.events.DoExactlyOnceStrategy
import summer.events.DoOnlyWhenAttachedStrategy
import summer.events.SummerEvent
import summer.state.InMemoryStateStrategy
import summer.state.InMemoryStore
import summer.state.InMemoryStoreProvider
import summer.state.SummerStateDelegate

/**
 * Base viewModel. Helps with view state restoring (see [SummerStateDelegate])
 * and executing of events (see [SummerEvent]).
 */
abstract class SummerViewModel<TView> :
    RestoreSummerViewModel<TView, InMemoryStoreProvider, ViewProvider<TView>>(),
    DoOnlyWhenAttachedStrategy.Factory<TView>,
    DoExactlyOnceStrategy.Factory<TView>,
    InMemoryStateStrategy.Factory<TView>,
    InMemoryStoreProvider {

    /**
     * Used for state storing by default. Can be overridden.
     */
    override val inMemoryStore = InMemoryStore()

    override fun getEventsOwner(): ViewProvider<TView> = this
    override fun getStateOwner(): InMemoryStoreProvider = this
}