package summer.example

import android.preference.PreferenceManager
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import summer.SummerLogger
import summer.example.domain.SharedUseCase
import summer.example.presentation.base.BasePresenter
import kotlin.reflect.KClass

class App : MultiDexApplication(), KodeinAware {

    override val kodein get() = di

    override fun onCreate() {
        super.onCreate()

        di = Kodein {

            import(sharedModule)

            // data
            bind<Settings>() with singleton { AndroidSettings(PreferenceManager.getDefaultSharedPreferences(applicationContext)) }

            bind<SummerLogger.Factory>() with singleton {
                object : SummerLogger.Factory {
                    override fun get(forClass: KClass<*>): SummerLogger {
                        val tag = forClass.java.simpleName
                        return object : SummerLogger {

                            override fun info(formatMessage: () -> String) {
                                Log.i(tag, formatMessage())
                            }

                            override fun error(e: Throwable) {
                                Log.e(tag, Log.getStackTraceString(e))
                            }
                        }
                    }
                }
            }

            bind() from singleton { BasePresenter.Dependencies(Dispatchers.IO, Dispatchers.Main, instance()) }
            bind() from singleton { SharedUseCase.Dependencies(Dispatchers.IO) }
        }

        kodeinAware = this
    }

    companion object {
        lateinit var kodeinAware: KodeinAware
    }
}