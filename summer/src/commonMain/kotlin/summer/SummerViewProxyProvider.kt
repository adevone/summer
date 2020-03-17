package summer

interface SummerViewProxyProvider<TView> {

    /**
     * Create proxy for view state. Proxy must contain all properties defined in TViewState.
     * Properties of proxy must use delegates created by [SummerPresenter.stateIn] method
     *
     * You can use summer plugin to make overriding of this method easier
     *
     * Example:
     *
     * object FeatureView {
     *
     *     interface State {
     *         var prop: Int
     *     }
     * }
     *
     * override val viewStateProxy = object : FeatureView.State {
     *     override var prop by store({ ::prop }, initial = 0)
     * }
     */
    val viewProxy: TView
}