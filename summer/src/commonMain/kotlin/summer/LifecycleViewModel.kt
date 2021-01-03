package summer

import summer.events.EventProxy
import summer.state.StateProxy

/**
 * Extent from this class if you want call [EventProxy.viewCreated] or
 * [StateProxy.restore] on a custom place.
 *
 * @param [TView] see [GetViewProvider]
 */
interface LifecycleViewModel<TView> :
    ViewModelController,
    GetViewHolder<TView> {

    override fun setViewProviderUnsafe(unsafeGetView: () -> Any?) {
        getView = {
            val view = unsafeGetView()
            @Suppress("UNCHECKED_CAST")
            view as TView?
        }
    }
}

interface GetViewHolder<TView> : GetViewProvider<TView> {
    override var getView: () -> TView?
}

/**
 * Non-generic protocol that can be used to call lifecycle events of [SummerViewModel]
 * in languages without covariant types support (like Swift).
 */
interface ViewModelController : ViewLifecycleListener {
    /**
     * Provide untyped view provider. It will be force converted to typed [GetViewProvider.getView].
     */
    fun setViewProviderUnsafe(unsafeGetView: () -> Any?)
}

interface ViewLifecycleListener {
    /**
     * Must be called when view is created. May be called multiple times.
     *
     * Important:
     * Do not override this method to listen view model lifecycle.
     * It will disable delegate for example [RestoreViewModel] if it used as a type delegate.
     * You can use it to implement your own base view model (for example with custom strategies).
     */
    fun viewCreated() {}
}

open class LifecycleViewModelImpl<TView> : LifecycleViewModel<TView> {
    override var getView: () -> TView? = { null }
}