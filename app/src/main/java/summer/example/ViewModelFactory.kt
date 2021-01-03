package summer.example

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.direct
import org.kodein.di.instance
import summer.example.presentation.*
import summer.example.presentation.base.BaseViewModel
import kotlin.reflect.KClass

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
                FrameworksViewModel(mainDI.direct.instance(), mainDI.direct.instance()) as T
            }
            MainViewModel::class.java -> {
                MainViewModel() as T
            }
            else -> throw IllegalArgumentException("modelClass can not be ${modelClass.name}")
        }
    }
}