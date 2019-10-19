package summer.android

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import summer.SummerPresenter
import summer.SummerPresenterWithRouter
import java.util.Collections.emptyList

abstract class SummerFragment<
        TViewState : Any,
        TViewMethods : Any,
        TPresenter : SummerPresenter<TViewState, TViewMethods>> : Fragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected abstract val viewMethods: TViewMethods

    protected abstract fun createViewState(): TViewState

    private var _presenter: TPresenter? = null
    protected val presenter: TPresenter get() = _presenter!!

    protected abstract fun createPresenter(): TPresenter

    protected abstract fun initView()

    protected var viewState: TViewState? = null

    protected open fun createComponents(): List<SummerComponent<*, *, *>> = emptyList()
    private var lifecycleComponents: List<SummerComponent<*, *, *>> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _presenter = createPresenter()
        lifecycleComponents = createComponents()
        lifecycleComponents.forEach { it.onCreate() }
        _presenter!!.created()
    }

    private var isFirstViewCreation = true

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewState = createViewState()
        lifecycleComponents.forEach { component ->
            component.onViewCreated(
                parentView = view as? ViewGroup,
                context = context!!,
                isFirstViewCreation = isFirstViewCreation
            )
        }
        presenter.viewCreated(viewState!!, viewMethods)
        if (isFirstViewCreation) {
            _presenter!!.entered()
            isFirstViewCreation = false
        }
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _presenter!!.viewDestroyed()
        lifecycleComponents.forEach { it.onDestroyView() }
        viewState = null
    }

    @CallSuper
    override fun onDestroy() {
        notifyPresenterIfRemoving()
        super.onDestroy()
        presenter.destroyed()
        lifecycleComponents.forEach { it.onDestroy() }
        lifecycleComponents = emptyList()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        presenter.appeared()
        lifecycleComponents.forEach { it.onResume() }
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        lifecycleComponents.forEach { it.onPause() }
        presenter.disappeared()
    }

    private fun notifyPresenterIfRemoving() {
        var anyParentIsRemoving = false

        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            presenter.exited()
            lifecycleComponents.forEach { it.onExit() }
        }
    }
}

abstract class SummerFragmentWithRouter<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any,
        TPresenter : SummerPresenterWithRouter<TViewState, TViewMethods, TRouter>>
    : SummerFragment<
        TViewState,
        TViewMethods,
        TPresenter> {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected abstract val router: TRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.routerCreated(router)
    }

    override fun onDestroy() {
        presenter.routerDestroyed()
        super.onDestroy()
    }
}