package summer

import summer.events.EventProxy
import summer.state.StateProxy

/**
 * Extent from this class if you want to call [EventProxy.viewCreated] or
 * [StateProxy.viewCreated] on a custom place.
 *
 * @param [TView] see [ViewStateProvider]
 */
interface LifecycleViewModel<TView> :
    ViewModelController,
    ViewStateHolder<TView> {

    override fun setViewProviderUnsafe(unsafeGetView: () -> Any?) {
        getView = {
            val view = unsafeGetView()
            @Suppress("UNCHECKED_CAST")
            view as TView?
        }
    }
}

interface ViewStateHolder<TView> : ViewStateProvider<TView> {
    override var getView: () -> TView?
    override var isViewAppeared: Boolean
}

/**
 * Non-generic protocol that can be used to call lifecycle events of [SummerViewModel]
 * in languages without covariant types support (like Swift).
 */
interface ViewModelController : ViewLifecycleListener {
    /**
     * Provide untyped view provider. It will be force converted to typed [ViewStateProvider.getView].
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

    /**
     * Must be called when view is appeared to a user.
     * May be called multiple times after one call of [viewCreated].
     *
     * Important:
     * Do not override this method to listen view model lifecycle.
     * It will disable delegate for example [RestoreViewModel] if it used as a type delegate.
     * You can use it to implement your own base view model (for example with custom strategies).
     */
    fun viewAppeared() {}

    /**
     * Must be called when view is disappeared to a user.
     * May be called multiple times after one call of [viewCreated]
     * independently with [viewAppeared].
     *
     * Important:
     * Do not override this method to listen view model lifecycle.
     * It will disable delegate for example [RestoreViewModel] if it used as a type delegate.
     * You can use it to implement your own base view model (for example with custom strategies).
     */
    fun viewDisappeared() {}
}

open class LifecycleViewModelImpl<TView> : LifecycleViewModel<TView> {
    override var getView: () -> TView? = { null }
    override var isViewAppeared: Boolean = false
}