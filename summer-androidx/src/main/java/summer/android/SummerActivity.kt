package summer.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import summer.SummerPresenter

abstract class SummerActivity<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any,
        TPresenter : SummerPresenter<TViewState, TViewMethods, TRouter>> : AppCompatActivity() {

    abstract val router: TRouter
    abstract val viewState: TViewState
    abstract val viewMethods: TViewMethods

    abstract fun createPresenter(): TPresenter

    lateinit var presenter: TPresenter

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        initView()
        presenter.created()
        presenter.viewCreated(viewState, viewMethods, router)
        presenter.entered()
    }

    abstract fun initView()

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
}