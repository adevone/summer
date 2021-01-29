package io.adev.summer.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.adev.summer.example.presentation.*

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            AboutViewModel::class.java -> {
                ServiceLocator.aboutViewModel() as T
            }
            BasketViewModel::class.java -> {
                ServiceLocator.basketViewModel() as T
            }
            FrameworkDetailsViewModel::class.java -> {
                ServiceLocator.frameworkDetailsViewModel() as T
            }
            FrameworksViewModel::class.java -> {
                ServiceLocator.frameworksViewModel() as T
            }
            MainViewModel::class.java -> {
                ServiceLocator.mainViewModel() as T
            }
            else -> throw IllegalArgumentException("modelClass can not be ${modelClass.name}")
        }
    }
}