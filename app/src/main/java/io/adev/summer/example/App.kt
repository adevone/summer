package io.adev.summer.example

import android.app.Application
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.singleton
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import io.adev.summer.example.ui.base.routing.LocalCiceroneHolder

class App : Application(), DIAware {

    override val di get() = mainDI

    override fun onCreate() {
        super.onCreate()

        mainDI = DI {
            import(sharedModule)

            bind<HttpClient>() with singleton { HttpClient(OkHttp) }
            bind<Json>() with singleton {
                Json {
                    ignoreUnknownKeys = true
                }
            }

            // navigation

            val cicerone = Cicerone.create()

            bind<Router>() with singleton { cicerone.router }
            bind<NavigatorHolder>() with singleton { cicerone.navigatorHolder }
            bind<LocalCiceroneHolder>() with singleton { LocalCiceroneHolder() }
        }

        kodeinAware = this
    }

    companion object {
        lateinit var kodeinAware: DIAware
    }
}