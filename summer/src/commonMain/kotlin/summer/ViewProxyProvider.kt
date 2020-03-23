package summer

import summer.events.EventFactory
import summer.events.SummerEvent
import summer.state.StateFactory

interface ViewProxyProvider<TView> {
    /**
     * Create proxy for view. Proxy must contain all properties defined in [TView].
     * Lambda-typed val properties must be created by [EventFactory.event] method and converted to
     * [SummerEvent] by [EventFactory.build] method.
     * All the rest properties must use delegates created by [StateFactory.state] method.
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
     *     override val method = event { it::prop }.doExactlyOnce()
     * }
     */
    val viewProxy: TView
}