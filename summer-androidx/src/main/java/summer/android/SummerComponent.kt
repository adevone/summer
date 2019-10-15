package summer.android

import android.content.Context
import android.view.View
import android.view.ViewGroup
import summer.SummerPresenter

abstract class SummerComponent<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any,
        TPresenter : SummerPresenter<TViewState, TViewMethods, TRouter>> {

    protected abstract val router: TRouter
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
        presenter.viewCreated(viewState, viewMethods, router)
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