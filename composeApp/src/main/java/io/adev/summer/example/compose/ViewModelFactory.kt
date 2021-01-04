package io.adev.summer.example.compose

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.adev.summer.example.presentation.*
import io.adev.summer.example.presentation.base.BaseViewModel
import kotlin.reflect.KClass

fun <TView, TViewModel : BaseViewModel<TView>> provideViewModel(
    viewModelClass: KClass<TViewModel>,
    activity: FragmentActivity
): TViewModel {
    val provider = ViewModelProvider(activity, ViewModelFactory())
    return provider[viewModelClass.java]
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