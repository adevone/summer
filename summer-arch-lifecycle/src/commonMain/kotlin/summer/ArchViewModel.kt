package summer

import summer.events.EventPerformerFactory
import summer.events.EventProxy
import summer.state.StateProxy

expect abstract class ArchViewModel<TView> constructor() :
    DefaultSummerViewModel<TView>,
    ViewProxyProvider<TView> {

    override fun eventProxyCreated(proxy: EventProxy<*, *>)
    override fun stateProxyCreated(proxy: StateProxy<*, *, *>)
    override fun getViewProvider(): GetViewProvider<TView>
    override fun viewCreated()
    override var getView: () -> TView?

    companion object : EventPerformerFactory
}