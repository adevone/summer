package summer.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import summer.DidSetMixin
import summer.SummerPresenter
import summer.SummerPresenterWithRouter

abstract class SummerActivity<
        TViewState,
        TViewMethods,
        TPresenter : SummerPresenter<TViewState, TViewMethods>> : AppCompatActivity() {

    protected abstract val viewState: TViewState
    protected abstract val viewMethods: TViewMethods

    protected abstract fun createPresenter(): TPresenter

    protected lateinit var presenter: TPresenter

    /**
     * Use initView in user code instead
     */
    @Suppress("UNCHECKED_CAST")
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        initView()
        presenter.created()
        initPresenterView()
        presenter.entered()
    }

    internal open fun initPresenterView() {
        presenter.viewCreated(viewState, viewMethods)
    }

    protected abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        presenter.viewDestroyed()
        presenter.destroyed()
    }

    override fun onResume() {
        super.onResume()
        presenter.appeared()
    }

    override fun onPause() {
        super.onPause()
        presenter.disappeared()
    }

    companion object : DidSetMixin()
}

abstract class SummerActivityWithRouter<
        TViewState,
        TViewMethods,
        TRouter,
        TPresenter : SummerPresenterWithRouter<TViewState, TViewMethods, TRouter>>
    : SummerActivity<TViewState, TViewMethods, TPresenter>() {

    protected abstract val router: TRouter

    override fun initPresenterView() {
        super.initPresenterView()
        presenter.routerCreated(router)
    }

    override fun onDestroy() {
        presenter.routerDestroyed()
        super.onDestroy()
    }
}