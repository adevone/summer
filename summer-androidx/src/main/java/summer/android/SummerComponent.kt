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

    protected abstract val key: String

    protected abstract val router: TRouter
    protected abstract val viewMethods: TViewMethods

    protected abstract val presenter: TPresenter

    protected abstract var viewState: TViewState

    protected abstract fun createView(
        parentView: ViewGroup?,
        context: Context
    ): View

    lateinit var view: View
    fun onViewCreated(parentView: ViewGroup?, context: Context, parentOwner: Any) {
        val owner = owner(parentOwner)
        presenter.beforeCreateView(owner = owner)
        view = createView(parentView, context)
        initView()
        presenter.onCreateView(viewState, viewMethods, router, owner)
    }

    protected abstract fun initView()

    fun onDestroyView() {
        presenter.onDestroyView()
    }

    fun onResume() {
        presenter.onAppear()
    }

    fun onPause() {
        presenter.onDisappear()
    }

    fun owner(parentOwner: Any) = Owner(parentOwner, key)

    data class Owner(
        val parentOwner: Any,
        val key: String
    )
}