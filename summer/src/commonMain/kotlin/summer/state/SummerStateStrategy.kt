package summer.state

import kotlin.reflect.KProperty

interface SummerStateStrategy<TOwner, T> {
    fun get(owner: TOwner, prop: KProperty<*>): T
    fun set(owner: TOwner, prop: KProperty<*>, value: T)
    fun isInit(owner: TOwner, prop: KProperty<*>): Boolean
}