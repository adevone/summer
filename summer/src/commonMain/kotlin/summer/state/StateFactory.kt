package summer.state

import summer.GetViewProvider

/**
 * DSL to create [SummerStateDelegate.Provider].
 *
 * [TView] see [SummerStateStrategy]
 * [TOwner] see [SummerStateStrategy]
 */
interface StateFactory<TView, TOwner> {
    /**
     * Creates provider of [SummerStateDelegate].
     *
     * May be called in viewProxy or just
     * in viewModel if some sort of persistent store is used
     * and you want to store viewModel state too.
     *
     * If view is not null than value will be saved in store
     * and mirrored in mirror property if view exists.
     *
     * If view is null than value will be only saved in store.
     */
    fun <T> state(
        getViewProperty: GetViewProperty<T, TView>? = null,
        initial: T,
        strategy: SummerStateStrategy<T, TOwner>
    ): SummerStateDelegate.Provider<T, TView, TOwner> {
        return SummerStateDelegate.Provider(
            stateOwner(),
            getViewProvider(),
            initial,
            strategy,
            getViewProperty = getViewProperty,
            delegateCreated = { delegate ->
                stateDelegateCreated(delegate)
            }
        )
    }

    fun stateOwner(): TOwner

    fun stateDelegateCreated(delegate: SummerStateDelegate<*, *, *>)

    fun getViewProvider(): GetViewProvider<TView>
}