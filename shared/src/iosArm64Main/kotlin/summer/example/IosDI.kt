package summer.example

import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import platform.Foundation.NSUserDefaults

fun bind(
    userDefaults: NSUserDefaults
) {
    di = Kodein {

        import(sharedModule)

        // data
        bind<Settings>() with singleton { AppleSettings(userDefaults) }
    }
}
