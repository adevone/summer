package summer

/**
 * Implemented in [EnterLifecycleSummerViewModelImpl].
 */
interface EnterLifecycleSummerViewModel<TView> : LifecycleSummerViewModel<TView>, EnterListener

interface EnterListener {
    /**
     * Called when [LifecycleSummerViewModel.viewCreated] was called first times.
     */
    fun onEnter() {}
}

/**
 * Implementation of [EnterLifecycleSummerViewModel].
 * Can be used as a base class or an interface delegate.
 *
 * Example of a delegation:
 * class MyViewModel<TView> :
 *     <parent class that prohibits inheritance from EnterLifecycleSummerViewModelImpl>,
 *     EnterLifecycleSummerViewModel<TView> by EnterLifecycleSummerViewModelImpl()
 */
open class EnterLifecycleSummerViewModelImpl<TView> : EnterLifecycleSummerViewModel<TView> {
    override var getView: () -> TView? = { null }

    private var viewCreatedWasCalled = false

    override fun viewCreated() {
        if (!viewCreatedWasCalled) {
            onEnter()
            viewCreatedWasCalled = true
        }
    }
}