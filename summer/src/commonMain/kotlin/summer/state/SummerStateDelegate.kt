package summer.state

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SummerStateDelegate<T, TOwner>(
    private val owner: TOwner,
    private val property: KProperty<*>,
    private val initial: T,
    private val strategy: SummerStateStrategy<T, TOwner>,
    private val setMirror: (T) -> Unit
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
        setMirror(value)
    }

    fun restore() {
        val isInit = strategy.wasStored(owner, property)
        if (isInit) {
            val value = strategy.get(owner, property)
            setMirror(value)
        } else {
            setMirror(initial)
        }
    }

    class Provider<T, TOwner>(
        private val owner: TOwner,
        private val initial: T,
        private val strategy: SummerStateStrategy<T, TOwner>,
        private val setMirror: (T) -> Unit,
        private val delegateCreated: (SummerStateDelegate<T, TOwner>) -> Unit
    ) {
        operator fun provideDelegate(
            thisRef: Any?,
            prop: KProperty<*>
        ): SummerStateDelegate<T, TOwner> {
            val delegate = SummerStateDelegate(owner, prop, initial, strategy, setMirror)
            delegateCreated(delegate)
            return delegate
        }
    }
}