package summer.state

import summer.GetViewProvider
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

class SummerStateDelegate<T, TView, TOwner>(
    private val owner: TOwner,
    private val getViewProvider: GetViewProvider<TView>,
    private val property: KProperty<*>,
    private val initial: T,
    private val strategy: SummerStateStrategy<T, TOwner>,
    private val getViewProperty: GetViewProperty<T, TView>?
) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val isInit = strategy.wasStored(owner, property)
        return if (isInit) {
            strategy.get(owner, property)
        } else {
            initial
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        strategy.set(owner, property, value)
        setViewPropertyIfViewExists(value)
    }

    fun restore() {
        val isInit = strategy.wasStored(owner, property)
        if (isInit) {
            val value = strategy.get(owner, property)
            setViewPropertyIfViewExists(value)
        } else {
            setViewPropertyIfViewExists(value = initial)
        }
    }

    private fun setViewPropertyIfViewExists(value: T) {
        val view = getViewProvider.getView()
        if (view != null) {
            getViewProperty?.let { getViewProperty ->
                val viewProperty = getViewProperty(view)
                viewProperty.set(value)
            }
        }
    }

    class Provider<T, TView, TOwner>(
        private val owner: TOwner,
        private val getViewProvider: GetViewProvider<TView>,
        private val initial: T,
        private val strategy: SummerStateStrategy<T, TOwner>,
        private val getViewProperty: GetViewProperty<T, TView>?,
        private val delegateCreated: (SummerStateDelegate<T, TView, TOwner>) -> Unit
    ) : PropertyDelegateProvider<Any?, SummerStateDelegate<T, TView, TOwner>> {

        override fun provideDelegate(
            thisRef: Any?,
            property: KProperty<*>
        ): SummerStateDelegate<T, TView, TOwner> {
            val delegate = SummerStateDelegate(
                owner,
                getViewProvider,
                property,
                initial,
                strategy,
                getViewProperty
            )
            delegateCreated(delegate)
            return delegate
        }
    }
}

/**
 * Provider of view property to mirror store state
 */
typealias GetViewProperty<T, TView> = (TView) -> KMutableProperty0<T>