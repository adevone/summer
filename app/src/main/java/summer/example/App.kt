package summer.example

import android.preference.PreferenceManager
import androidx.multidex.MultiDexApplication
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import summer.ExceptionsHandler
import summer.example.domain.SharedUseCase
import summer.example.presentation.base.BasePresenter
import summer.log.AndroidLogCollectorsFactory
import summer.log.LogCollectorsProvider
import summer.log.logCollectorsProvider

class App : MultiDexApplication(), KodeinAware {

    override val kodein get() = di

    override fun onCreate() {
        super.onCreate()
        logCollectorsProvider = LogCollectorsProvider(AndroidLogCollectorsFactory)

        di = Kodein {

            import(sharedModule)

            // data
            bind<Settings>() with singleton { AndroidSettings(PreferenceManager.getDefaultSharedPreferences(applicationContext)) }

            // common
            bind<ExceptionsHandler>() with singleton {
                object : ExceptionsHandler {
                    override fun handle(e: Throwable) {
                        // handle them all!
                    }
                }
            }

            bind() from singleton { BasePresenter.Dependencies(instance(), Dispatchers.IO, Dispatchers.Main) }
            bind() from singleton { SharedUseCase.Dependencies(Dispatchers.IO) }
        }

        kodeinAware = this
    }

    companion object {
        lateinit var kodeinAware: KodeinAware
    }
}