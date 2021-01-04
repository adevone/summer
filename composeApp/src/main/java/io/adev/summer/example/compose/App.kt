package io.adev.summer.example.compose

import android.app.Application
import io.adev.summer.example.mainDI
import io.adev.summer.example.sharedModule
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.singleton

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
        }

        kodeinAware = this
        instance = this
    }

    companion object {
        lateinit var instance: App
        lateinit var kodeinAware: DIAware
    }
}