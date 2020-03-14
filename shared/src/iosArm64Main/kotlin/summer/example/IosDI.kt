package summer.example

import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import platform.Foundation.NSUserDefaults

fun bind(
    userDefaults: NSUserDefaults
) {
    di = Kodein {
        import(sharedModule)

        bind<Settings>() with singleton { AppleSettings(userDefaults) }
        bind<HttpClient>() with singleton { HttpClient(Ios) }
        bind<Json>() with singleton { Json(JsonConfiguration.Stable.copy(isLenient = true)) }
    }
}
