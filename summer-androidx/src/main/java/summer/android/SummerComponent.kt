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
        presenter.onCreate()
    }

    fun onDestroy() {
        presenter.onDestroy()
    }

    private var _view: View? = null
    val view: View get() = _view!!

    fun onViewCreated(parentView: ViewGroup?, context: Context, isFirstViewCreation: Boolean) {
        _view = createView(parentView, context)
        initView()
        presenter.onCreateView(viewState, viewMethods, router)
        if (isFirstViewCreation) {
            presenter.afterCreate()
        }
    }

    protected abstract fun initView()

    fun onDestroyView() {
        presenter.onDestroyView()
        _view = null
    }

    fun onResume() {
        presenter.onAppear()
    }

    fun onPause() {
        presenter.onDisappear()
    }
}