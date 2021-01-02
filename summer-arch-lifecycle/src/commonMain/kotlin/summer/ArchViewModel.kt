package summer

import summer.events.SummerEvent
import summer.state.InMemoryStore
import summer.state.InMemoryStoreProvider
import summer.state.SummerStateDelegate

expect abstract class ArchViewModel<TView> constructor() :
    DefaultSummerViewModel<TView>,
    ViewProxyProvider<TView> {

    override var getView: () -> TView?
    override val inMemoryStore: InMemoryStore
    override fun eventCreated(event: SummerEvent<*, *>)
    override fun eventsOwner(): Any?
    override fun getViewProvider(): GetViewProvider<TView>
    override fun stateDelegateCreated(delegate: SummerStateDelegate<*, *, *>)
    override fun stateOwner(): InMemoryStoreProvider
    override fun viewCreated()
}