package summer

import summer.events.EventProxy
import summer.state.StateProxy
import summer.state.strategies.InMemoryStore

expect abstract class ArchViewModel<TView> constructor() :
    DefaultSummerViewModel<TView>,
    ViewProxyProvider<TView> {

    override var getView: () -> TView?
    override val inMemoryStore: InMemoryStore
    override fun eventProxyCreated(proxy: EventProxy<*, *>)
    override fun eventProxyOwner(): Any?
    override fun stateProxyCreated(proxy: StateProxy<*, *, *>)
    override fun getViewProvider(): GetViewProvider<TView>
    override fun viewCreated()
}