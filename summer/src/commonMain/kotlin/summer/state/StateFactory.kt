package summer.state

import summer.ViewProvider
import kotlin.reflect.KMutableProperty0

interface StateFactory<TOwner, TView> : ViewProvider<TView> {

    /**
     * Create delegate for property stored in any store.
     *
     * May be called in viewProxy or just
     * in presenter if some sort of persistent store is used.
     *
     * If view is not null value will be stored in store
     * and mirrored in mirror property if view exists.
     *
     * If view is null value will be stored only in store.
     *
     * @return state property delegate
     */
    fun <T> state(
        getMirrorProperty: GetMirrorProperty<TView, T>? = null,
        initial: T,
        strategy: SummerStateStrategy<TOwner, T>
    ): StateDelegateProvider<T, TOwner> {
        return StateDelegateProvider(
            getOwner(),
            mirrorOnSet(getMirrorProperty),
            initial,
            strategy
        ) { delegate ->
            stateDelegateCreated(delegate)
        }
    }

    fun getOwner(): TOwner

    fun stateDelegateCreated(delegate: StateDelegate<*, TOwner>)
}

fun <TView, T> ViewProvider<TView>.mirrorOnSet(
    getMirrorProperty: GetMirrorProperty<TView, T>?
): (T) -> Unit = { value ->
    val currentView = getView()
    if (currentView != null && getMirrorProperty != null) {
        val property = getMirrorProperty(currentView)
        property.set(value)
    }
}

typealias GetMirrorProperty<TView, T> = (TView) -> KMutableProperty0<T>

