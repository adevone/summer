package summer.state

import summer.ViewProvider
import kotlin.reflect.KMutableProperty0

/**
 * DSL to create [SummerStateDelegate.Provider].
 *
 * [TView] see [SummerStateStrategy]
 * [TOwner] see [SummerStateStrategy]
 */
interface StateFactory<TView, TOwner> : ViewProvider<TView> {

    /**
     * Creates provider of [SummerStateDelegate].
     *
     * May be called in viewProxy or just
     * in presenter if some sort of persistent store is used
     * and you want to store presenter state too.
     *
     * If view is not null than value will be saved in store
     * and mirrored in mirror property if view exists.
     *
     * If view is null than value will be only saved in store.
     */
    fun <T> state(
        getMirrorProperty: GetMirrorProperty<TView, T>? = null,
        initial: T,
        strategy: SummerStateStrategy<T, TOwner>
    ): SummerStateDelegate.Provider<T, TOwner> {
        return SummerStateDelegate.Provider(
            getStateOwner(),
            initial,
            strategy,
            setMirror = setMirrorIfViewExists(getMirrorProperty),
            delegateCreated = { delegate ->
                stateDelegateCreated(delegate)
            }
        )
    }

    fun getStateOwner(): TOwner

    fun stateDelegateCreated(delegate: SummerStateDelegate<*, TOwner>)
}

/**
 * Mirror state changes to view only if it exists.
 */
fun <TView, T> ViewProvider<TView>.setMirrorIfViewExists(
    getMirrorProperty: GetMirrorProperty<TView, T>?
): (T) -> Unit = { value ->
    val currentView = getView()
    if (currentView != null && getMirrorProperty != null) {
        val property = getMirrorProperty(currentView)
        property.set(value)
    }
}

/**
 * Provider of view property to mirror store state
 */
typealias GetMirrorProperty<TView, T> = (TView) -> KMutableProperty0<T>

