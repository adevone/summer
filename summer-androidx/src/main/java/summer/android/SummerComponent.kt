package summer.android

import android.content.Context
import android.view.View
import android.view.ViewGroup
import summer.SummerPresenter
import summer.SummerPresenterWithRouter

abstract class SummerComponent<
        TView : Any,
        TPresenter : SummerPresenter<TView>> {

    protected abstract val presenter: TPresenter

    protected abstract var viewState: TView

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
        presenter.viewCreated(viewState)
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

    companion object : DidSetMixin()
}

abstract class SummerComponentWithRouter<
        TViewState : Any,
        TRouter : Any,
        TPresenter : SummerPresenterWithRouter<TViewState, TRouter>>
    : SummerComponent<
        TViewState,
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