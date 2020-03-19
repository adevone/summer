package summer.example.ui.base

import summer.android.SummerActivity
import summer.example.AppKodeinAware
import summer.example.presentation.base.BasePresenter

abstract class BaseActivity : SummerActivity(), AppKodeinAware {

    abstract val presenter: BasePresenter<*>

    override fun onResume() {
        super.onResume()
        presenter.onAppear()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDisappear()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}