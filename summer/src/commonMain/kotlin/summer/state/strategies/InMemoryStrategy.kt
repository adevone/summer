package summer.state.strategies

import summer.ViewStateProvider
import summer.state.*
import kotlin.reflect.KProperty

/**
 * Saves state in the inner variable.
 */
class InMemoryStrategy<T, TView> : StateProxyStrategy<T, TView, Nothing?> {

    private var currentValue: T? = null

    // null in currentValue does not mean that [currentValue] was never set
    // because [T] can be nullable. Need a separate variable to determine it.
    private var wasSet = false

    override fun getValue(
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: Nothing?,
        viewStateProvider: ViewStateProvider<TView>,
    ): T {
        return if (wasSet) {
            @Suppress("UNCHECKED_CAST")
            currentValue as T
        } else {
            initial
        }
    }

    override fun setValue(
        value: T,
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: Nothing?,
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        wasSet = true
        currentValue = value
        val view = viewStateProvider.getView()
        if (view != null) {
            viewPropertySetter.setIfExists(value, view)
        }
    }

    override fun viewCreated(
        initial: T,
        viewPropertySetter: ViewPropertySetter<T, TView>,
        proxyProperty: KProperty<*>,
        owner: Nothing?,
        viewStateProvider: ViewStateProvider<TView>,
    ) {
        val view = viewStateProvider.getView()
        if (view != null) {
            if (wasSet) {
                @Suppress("UNCHECKED_CAST")
                val value = currentValue as T
                viewPropertySetter.setIfExists(value, view)
            } else {
                viewPropertySetter.setIfExists(initial, view)
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
                InMemoryStrategy(),
                owner = null,
            )
        }
    }
}