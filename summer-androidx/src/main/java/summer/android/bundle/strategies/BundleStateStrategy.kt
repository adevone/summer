package summer.android.bundle.strategies

import android.os.Bundle
import summer.android.bundle.BundleProvider
import summer.state.SummerStateStrategy
import kotlin.reflect.KProperty

abstract class BundleStateStrategy<T>(
    private val getValue: Bundle.(String?) -> T,
    private val setValue: Bundle.(String?, T) -> Unit
) : SummerStateStrategy<T, BundleProvider> {

    override fun get(owner: BundleProvider, prop: KProperty<*>): T {
        return owner.bundle.getValue(prop.name)
    }

    override fun isInit(owner: BundleProvider, prop: KProperty<*>): Boolean {
        return owner.bundle.containsKey(prop.name)
    }

    override fun set(owner: BundleProvider, prop: KProperty<*>, value: T) {
        owner.bundle.setValue(prop.name, value)
    }
}