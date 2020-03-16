package summer.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import summer.SummerPresenter
import summer.SummerPresenterWithRouter

abstract class SummerActivity<
        TView,
        TPresenter : SummerPresenter<TView>> : AppCompatActivity() {

    protected abstract val view: TView

    protected abstract fun createPresenter(): TPresenter

    protected lateinit var presenter: TPresenter

    private var isCreating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        isCreating = true
        presenter = createPresenter()
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (isCreating) {
            presenter.created()
            initPresenterView()
            presenter.entered()
        }
        isCreating = false
    }

    internal open fun initPresenterView() {
        presenter.viewCreated(view)
    }

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
        TRouter,
        TPresenter : SummerPresenterWithRouter<TViewState, TRouter>>
    : SummerActivity<TViewState, TPresenter>() {

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