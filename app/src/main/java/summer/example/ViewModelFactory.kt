package summer.example

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import summer.example.presentation.*
import summer.example.presentation.base.BaseViewModel
import kotlin.reflect.KClass

fun <TView, TViewModel : BaseViewModel<TView>> bindViewModel(
    viewModelClass: KClass<TViewModel>,
    fragment: Fragment,
    provideView: () -> TView
): TViewModel {
    val provider = ViewModelProvider(fragment, ViewModelFactory())
    val viewModel = provider[viewModelClass.java]
    viewModel.bindView(fragment.viewLifecycleOwner, provideView)
    return viewModel
}

fun <TView, TViewModel : BaseViewModel<TView>> bindViewModel(
    viewModelClass: KClass<TViewModel>,
    activity: FragmentActivity,
    provideView: () -> TView
): TViewModel {
    val provider = ViewModelProvider(activity, ViewModelFactory())
    val viewModel = provider[viewModelClass.java]
    viewModel.bindView(activity, provideView)
    return viewModel
}

private class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            AboutViewModel::class.java -> {
                AboutViewModel() as T
            }
            BasketViewModel::class.java -> {
                BasketViewModel() as T
            }
            FrameworkDetailsViewModel::class.java -> {
                FrameworkDetailsViewModel() as T
            }
            FrameworksViewModel::class.java -> {
                FrameworksViewModel() as T
            }
            MainViewModel::class.java -> {
                MainViewModel() as T
            }
            else -> throw IllegalArgumentException("modelClass can not be ${modelClass.name}")
        }
    }
}