package summer.state

import kotlin.reflect.KProperty

/**
 * Determines how state will be stored.
 *
 * [T] type of stored value.
 *
 * [TOwner] is strategy dependencies container. If you want to pass some dependencies to
 *          strategy than define it on your custom interface and implement it
 *          on your [LifecycleViewModel].
 *          Store should be provided through [TOwner].
 */
interface SummerStateStrategy<T, TOwner> {
    /**
     * Obtain value from store.
     * [prop] is property delegated by associated [SummerStateDelegate]
     */
    fun get(owner: TOwner, prop: KProperty<*>): T

    /**
     * Save value in store.
     * [prop] see [get]
     */
    fun set(owner: TOwner, prop: KProperty<*>, value: T)

    /**
     * Is store have value to [prop] (see [get])
     */
    fun wasStored(owner: TOwner, prop: KProperty<*>): Boolean
}