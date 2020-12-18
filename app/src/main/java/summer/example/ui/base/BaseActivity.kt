package summer.example.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import summer.LifecycleSummerViewModel
import summer.android.BaseSummerActivity
import summer.android.SummerViewModelProvider
import summer.example.AppKodeinAware
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.ViewModelEventsListener
import kotlin.reflect.KClass

abstract class BaseActivity : BaseSummerActivity<SummerViewModelProvider<*, *>>(), AppKodeinAware {

    abstract val viewModel: BaseViewModel<*>

    lateinit var viewClazz: KClass<*>
    lateinit var viewModelClazz: KClass<*>

    inline fun <reified TView, reified TViewModel : LifecycleSummerViewModel<TView>> TView.bindViewModel(
        noinline createViewModel: () -> TViewModel
    ): SummerViewModelProvider<TView, TViewModel> {
        val provider = SummerViewModelProvider(createViewModel, view = this)
        assignViewModelProvider(provider)
        viewClazz = TView::class
        viewModelClazz = TViewModel::class
        return provider
    }

    fun assignViewModelProvider(provider: SummerViewModelProvider<*, *>) {
        viewModelProvider = provider
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelEventsListener.onAttach(viewModelClazz, viewClazz)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        ViewModelEventsListener.onDetach(viewModelClazz)
    }
}