package summer

import summer.events.SummerEvent
import summer.state.SummerStateDelegate

/**
 * Extent from this class if you want call [SummerEvent.viewCreated] or
 * [SummerStateDelegate.restore] on custom place.
 *
 * [TView] is type of associated view.
 */
interface LifecycleSummerViewModel<TView> :
    ViewModelController,
    ViewProvider<TView>, ViewProxyProvider<TView> {

    override var getView: () -> TView?

    override fun setViewProviderUnsafe(unsafeGetView: () -> Any?) {
        getView = {
            val view = unsafeGetView()
            @Suppress("UNCHECKED_CAST")
            view as TView?
        }
    }
}

/**
 * Non-generic protocol that can be used to call lifecycle events of [SummerViewModel]
 * in languages without covariant types support (like Swift).
 */
interface ViewModelController {
    /**
     * Must be called when view is created. May be called multiple times.
     */
    fun viewCreated()

    /**
     * Provide untyped view provider. It will be force converted to typed [ViewProvider.getView].
     */
    fun setViewProviderUnsafe(unsafeGetView: () -> Any?)
}