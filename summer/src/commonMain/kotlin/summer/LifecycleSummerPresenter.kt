package summer

import summer.events.EventFactory
import summer.state.StateFactory

/**
 * Extent from this class if you want to implement
 * custom [EventFactory] or [StateFactory]
 *
 * [TView] is type of associated view.
 */
interface LifecycleSummerPresenter<TView> :
    PresenterController,
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
 * Non-generic protocol that can be used to call lifecycle events of [SummerPresenter]
 * in languages without covariant types support (like Swift).
 */
interface PresenterController {
    /**
     * Must be called when view is created. May be called multiple times.
     */
    fun viewCreated()

    fun setViewProviderUnsafe(unsafeGetView: () -> Any?)
}