package summer.example

import androidx.multidex.MultiDexApplication
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import summer.example.ui.base.routing.LocalCiceroneHolder

class App : MultiDexApplication(), KodeinAware {

    override val kodein get() = di

    override fun onCreate() {
        super.onCreate()

        di = Kodein {
            import(sharedModule)

            bind<HttpClient>() with singleton { HttpClient(OkHttp) }
            bind<Json>() with singleton { Json(JsonConfiguration.Stable.copy(isLenient = true)) }

            // navigation

            val cicerone = Cicerone.create()

            bind<Router>() with singleton { cicerone.router }
            bind<NavigatorHolder>() with singleton { cicerone.navigatorHolder }
            bind<LocalCiceroneHolder>() with singleton { LocalCiceroneHolder() }
        }

        kodeinAware = this
    }

    companion object {
        lateinit var kodeinAware: KodeinAware
    }
}