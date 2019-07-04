package summer.android

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import summer.SummerPresenter
import summer.summer.StateHolder

abstract class SummerFragment<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any,
        TPresenter : SummerPresenter<TViewState, TViewMethods, TRouter>> : Fragment() {

    protected abstract val stateHolder: StateHolder

    protected abstract val router: TRouter
    protected abstract val viewMethods: TViewMethods

    protected abstract fun createViewState(): TViewState

    private var _presenter: TPresenter? = null
    protected val presenter: TPresenter get() = _presenter!!
    protected abstract fun createPresenter(): TPresenter

    protected abstract fun initView()

    protected var viewState: TViewState? = null

    protected open fun createComponents(): List<SummerComponent<*, *, *, *>> = emptyList()
    private var lifecycleComponents: List<SummerComponent<*, *, *, *>> = emptyList()

    private val componentOwners = mutableListOf<SummerComponent.Owner>()

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _presenter = createPresenter()
        presenter.beforeCreateView(owner = this)
        initView()
        viewState = createViewState()
        presenter.onCreateView(viewState!!, viewMethods, router, owner = this)

        lifecycleComponents = createComponents()
        componentOwners += lifecycleComponents.map { it.owner(parentOwner = this) }
        lifecycleComponents.forEach { component ->
            component.onViewCreated(
                parentView = view as? ViewGroup,
                context = context!!,
                parentOwner = this
            )
        }
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _presenter!!.onDestroyView()
        lifecycleComponents.forEach { it.onDestroyView() }
        lifecycleComponents = emptyList()
        viewState = null
//        _presenter = null // иначе не получится получить доступ к onDestroyOwner()
    }

    @CallSuper
    override fun onDestroy() {
        notifyPresenterIfRemoving()
        super.onDestroy()
        componentOwners.forEach { stateHolder.onDestroyOwner(it) }
        _presenter?.onDestroyOwner()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        presenter.onAppear()
        lifecycleComponents.forEach { it.onResume() }
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        lifecycleComponents.forEach { it.onPause() }
        presenter.onDisappear()
    }

    private fun notifyPresenterIfRemoving() {
        var anyParentIsRemoving = false

        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) presenter.onPop()
    }
}

abstract class ArgsSummerFragment<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any,
        TPresenter : SummerPresenter<TViewState, TViewMethods, TRouter>,
        TArgs> : SummerFragment<
        TViewState,
        TViewMethods,
        TRouter,
        TPresenter>(), ArgsFragmentFeature<TArgs> {

    @Suppress("LeakingThis")
    override val fragment = this

    override var argsBackingField: TArgs? = null
}