package summer.example.ui.base

import summer.android.SummerActivity
import summer.example.AppKodeinAware
import summer.example.presentation.base.BaseViewModel

abstract class BaseActivity : SummerActivity(), AppKodeinAware {

    abstract val viewModel: BaseViewModel<*>

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}