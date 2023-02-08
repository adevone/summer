package summer

import summer.events.EventProxy
import summer.events.EventProxyFactory
import summer.state.StateProxyFactory

interface ViewProxyProvider<TView> {
    /**
     * Create proxy for view. Proxy must contain all properties defined in [TView].
     * Lambda-typed val properties must be created by [EventProxyFactory.event] method and converted to
     * [EventProxy] by [EventProxyFactory.using] method.
     * All the rest properties must use delegates created by [StateProxyFactory.state] method.
     *
     * You can use Intellij plugin to make overriding of this property easier.
     * @see [Summer plugin](https://github.com/adevone/summer-plugin)
     *
     * Example:
     *
     * interface FeatureView {
     *     var prop: Int
     *     val method: (Int) -> Unit
     * }
     *
     * override val viewProxy = object : FeatureView {
     *     override var prop by state({ it::prop }, initial = 0)
     *     override val method = event { it.method }.perform.exactlyOnce()
     * }
     */
    val viewProxy: TView
}