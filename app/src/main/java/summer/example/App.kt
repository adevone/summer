package summer.example

import android.util.Log
import android.app.Application
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.singleton
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import summer.example.recording.InputStep
import summer.example.recording.steps
import summer.example.ui.base.routing.LocalCiceroneHolder
import java.io.File
import kotlin.system.exitProcess

class App : Application(), DIAware {

    override val di get() = mainDI

    override fun onCreate() {
        super.onCreate()

        val crashLogFile = File(filesDir, "today.log")
        if (crashLogFile.exists()) {
            val crashText = crashLogFile.readText()
            Log.e("ERROR", crashText)
            crashLogFile.delete()
        }

        Thread.currentThread().setUncaughtExceptionHandler { _, _ ->
            val json = Json.encodeToString(ListSerializer(InputStep.serializer()), steps)
            if (!crashLogFile.exists()) {
                crashLogFile.createNewFile()
            }
            crashLogFile.writeText(json)
            Log.e("ERROR", json)
            exitProcess(1)
        }

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