package summer.android

import android.content.Context
import android.view.View
import android.view.ViewGroup
import summer.SummerPresenter
import summer.SummerPresenterWithRouter

abstract class SummerComponent<
        TViewState : Any,
        TViewMethods : Any,
        TPresenter : SummerPresenter<TViewState, TViewMethods>> {

    protected abstract val viewMethods: TViewMethods

    protected abstract val presenter: TPresenter

    protected abstract var viewState: TViewState

    protected abstract fun createView(
        parentView: ViewGroup?,
        context: Context
    ): View

    fun onCreate() {
        presenter.created()
    }

    fun onDestroy() {
        presenter.destroyed()
    }

    private var _view: View? = null
    val view: View get() = _view!!

    fun onViewCreated(parentView: ViewGroup?, context: Context, isFirstViewCreation: Boolean) {
        _view = createView(parentView, context)
        initView()
        initPresenterView()
        if (isFirstViewCreation) {
            presenter.entered()
        }
    }

    internal open fun initPresenterView() {
        presenter.viewCreated(viewState, viewMethods)
    }

    protected abstract fun initView()

    open fun onDestroyView() {
        presenter.viewDestroyed()
        _view = null
    }

    fun onResume() {
        presenter.appeared()
    }

    fun onPause() {
        presenter.disappeared()
    }

    fun onExit() {
        presenter.exited()
    }
}

abstract class SummerComponentWithRouter<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any,
        TPresenter : SummerPresenterWithRouter<TViewState, TViewMethods, TRouter>>
    : SummerComponent<
        TViewState,
        TViewMethods,
        TPresenter>() {

    protected abstract val router: TRouter

    override fun initPresenterView() {
        super.initPresenterView()
        presenter.routerCreated(router)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.viewDestroyed()
    }
}