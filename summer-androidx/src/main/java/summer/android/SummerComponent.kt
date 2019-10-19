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

    open fun onCreate() {
        presenter.created()
    }

    open fun onDestroy() {
        presenter.destroyed()
    }

    private var _view: View? = null
    val view: View get() = _view!!

    fun onViewCreated(parentView: ViewGroup?, context: Context, isFirstViewCreation: Boolean) {
        _view = createView(parentView, context)
        initView()
        presenter.viewCreated(viewState, viewMethods)
        if (isFirstViewCreation) {
            presenter.entered()
        }
    }

    protected abstract fun initView()

    fun onDestroyView() {
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

    override fun onCreate() {
        super.onCreate()
        presenter.routerCreated(router)
    }

    override fun onDestroy() {
        presenter.viewDestroyed()
        super.onDestroy()
    }
}