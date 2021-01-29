package io.adev.summer.example

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import io.adev.summer.example.presentation.base.BaseViewModel
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