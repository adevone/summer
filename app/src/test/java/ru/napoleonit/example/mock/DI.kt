package ru.napoleonit.example.mock

import android.util.Log
import kotlinx.coroutines.Dispatchers
import summer.SummerPresenter
import summer.SummerLogger
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import summer.example.domain.SharedUseCase
import summer.example.domain.debt.data.DebtDao
import summer.example.entity.Debt
import summer.example.presentation.base.BasePresenter
import kotlin.reflect.KClass

val mockModule = Kodein.Module("mock") {

    val mockDebtDao = object : DebtDao {
        override var debt: Debt? = null
        override var loan: Float? = null
    }

    bind<SummerLogger.Factory>() with singleton {
        object : SummerLogger.Factory {
            override fun get(forClass: KClass<*>): SummerLogger {
                val tag = forClass.java.simpleName
                return object : SummerLogger {

                    override fun info(formatMessage: () -> String) {
                        println(tag + ": " + formatMessage())
                    }

                    override fun error(e: Throwable) {
                        println(tag + ": " + Log.getStackTraceString(e))
                    }
                }
            }
        }
    }

    bind<DebtDao>() with singleton { mockDebtDao }

    bind() from singleton { BasePresenter.Dependencies(Dispatchers.Unconfined, Dispatchers.Unconfined, instance()) }
    bind() from singleton { SharedUseCase.Dependencies(Dispatchers.Unconfined) }
}

private val mockKodein = Kodein {
    import(mockModule)
}

interface MockKodeinAware : KodeinAware {
    override val kodein get() = mockKodein
}

inline fun <reified T : Any> MockKodeinAware.instance() = direct.instance<T>()