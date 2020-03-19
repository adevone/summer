package summer.example.ui.base

import summer.android.SummerActivity
import summer.example.AppKodeinAware
import summer.example.presentation.base.BasePresenter

abstract class BaseActivity : SummerActivity(), AppKodeinAware {

    abstract val presenter: BasePresenter<*>

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}