package summer.example.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import summer.DidSetMixin
import summer.example.AppKodeinAware
import summer.example.ViewModelFactory
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.ViewModelEventsListener
import kotlin.reflect.KClass

abstract class BaseActivity : AppCompatActivity(), AppKodeinAware {
    companion object : DidSetMixin

    lateinit var viewClazz: KClass<*>
    lateinit var viewModelClazz: KClass<*>
    inline fun <reified TView, reified TViewModel : BaseViewModel<TView, *>> bindViewModel(
        viewModelClass: KClass<TViewModel>,
        activity: FragmentActivity,
        noinline provideView: () -> TView,
    ): TViewModel {
        viewClazz = TView::class
        viewModelClazz = TViewModel::class
        val provider = ViewModelProvider(activity, ViewModelFactory())
        val viewModel = provider[viewModelClass.java]
        viewModel.bindView(activity, provideView)
        ViewModelEventsListener.onAttach(viewModelClazz, viewClazz)
        return viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        ViewModelEventsListener.onDetach(viewModelClazz)
    }
}