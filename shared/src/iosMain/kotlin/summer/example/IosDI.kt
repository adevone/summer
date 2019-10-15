package summer.example

import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import platform.Foundation.NSUserDefaults
import summer.example.domain.SharedUseCase
import summer.example.presentation.base.BasePresenter

fun bind(
    userDefaults: NSUserDefaults
) {
    di = Kodein {

        import(sharedModule)

        // data
        bind<Settings>() with singleton { AppleSettings(userDefaults) }

        bind() from singleton { BasePresenter.Dependencies(Dispatchers.Unconfined, Dispatchers.Unconfined, instance()) }
        bind() from singleton { SharedUseCase.Dependencies(Dispatchers.Unconfined) }
    }
}
