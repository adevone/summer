package summer.state

import kotlin.reflect.KProperty

interface SummerStateStrategy<T, TOwner> {
    fun get(owner: TOwner, prop: KProperty<*>): T
    fun set(owner: TOwner, prop: KProperty<*>, value: T)
    fun wasStored(owner: TOwner, prop: KProperty<*>): Boolean
}