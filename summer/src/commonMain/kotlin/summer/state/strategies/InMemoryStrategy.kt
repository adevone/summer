package summer.state.strategies

import summer.GetViewProvider
import summer.state.*

/**
 * Saves state in the inner variable.
 */
class InMemoryStrategy<T, TView> : StateProxyStrategy<T, TView, Nothing?> {
    private var wasSet = false
    private var currentValue: T? = null

    override fun getValue(
        viewProperty: ViewProperty<T, TView, Nothing?>,
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ): T {
        return if (wasSet) {
            @Suppress("UNCHECKED_CAST")
            currentValue as T
        } else {
            viewProperty.initial
        }
    }

    override fun setValue(
        value: T,
        viewProperty: ViewProperty<T, TView, Nothing?>,
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        wasSet = true
        currentValue = value
        val view = getViewProvider.getView()
        if (view != null) {
            viewProperty.setIfExists(value, view)
        }
    }

    override fun viewCreated(
        viewProperty: ViewProperty<T, TView, Nothing?>,
        owner: Nothing?,
        getViewProvider: GetViewProvider<TView>,
    ) {
        val view = getViewProvider.getView()
        if (view != null) {
            if (wasSet) {
                @Suppress("UNCHECKED_CAST")
                val value = currentValue as T
                viewProperty.setIfExists(value, view)
            } else {
                viewProperty.setIfExists(viewProperty.initial, view)
            }
        }
    }

    interface ProxyFactory<TView> : StateProxyFactory<TView> {

        fun <T> state(
            getViewProperty: GetViewProperty<T, TView>? = null,
            initial: T,
        ): StateProxy.Provider<T, TView, Nothing?> {
            return state(
                getViewProperty,
                initial,
                InMemoryStrategy()
            )
        }
    }
}