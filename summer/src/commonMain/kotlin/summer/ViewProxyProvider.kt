package summer

interface ViewProxyProvider<TView> {
    /**
     * Create proxy for view state. Proxy must contain all properties defined in TViewState.
     * Properties of proxy must use delegates created by [SummerPresenter.state] method.
     *
     * You can use summer plugin to make overriding of this method easier.
     *
     * Example:
     *
     * interface FeatureView {
     *     var prop: Int
     * }
     *
     * override val viewProxy = object : FeatureView {
     *     override var prop by state({ it::prop }, initial = 0)
     * }
     */
    val viewProxy: TView
}