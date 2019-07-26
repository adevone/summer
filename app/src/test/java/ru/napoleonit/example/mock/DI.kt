package ru.napoleonit.example.mock

import kotlinx.coroutines.Dispatchers
import nap.SummerPresenter
import nap.summer.ExceptionsHandler
import nap.summer.StateHolder
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import summer.example.domain.SharedUseCase
import summer.example.domain.debt.data.DebtDao
import summer.example.entity.Debt

val mockModule = Kodein.Module("mock") {

    val mockDebtDao = object : DebtDao {
        override var debt: Debt? = null
        override var loan: Float? = null
    }

    bind<DebtDao>() with singleton { mockDebtDao }

    // presentation
    bind<ExceptionsHandler>() with singleton {
        object : ExceptionsHandler {
            override fun handle(e: Throwable) {
                throw e
            }
        }
    }

    bind() from singleton { SummerPresenter.Dependencies(instance(), instance(), Dispatchers.Unconfined, Dispatchers.Unconfined) }
    bind() from singleton { SharedUseCase.Dependencies(Dispatchers.Unconfined) }
    bind() from singleton { StateHolder() }
}

private val mockKodein = Kodein {
    import(mockModule)
}

interface MockKodeinAware : KodeinAware {
    override val kodein get() = mockKodein
}

inline fun <reified T : Any> MockKodeinAware.instance() = direct.instance<T>()