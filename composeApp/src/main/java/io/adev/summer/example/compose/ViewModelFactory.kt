package io.adev.summer.example.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.adev.summer.example.presentation.*

@Composable
inline fun <reified VM : ViewModel> getViewModel(): VM = viewModel(factory = ViewModelFactory())

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

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