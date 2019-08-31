package summer.example

import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import summer.example.data.PrefsDebtDao
import summer.example.domain.debt.GetDebt
import summer.example.domain.debt.data.DebtDao

lateinit var di: Kodein

val sharedModule = Kodein.Module("shared") {

    // domain
    bind<GetDebt>() with singleton { GetDebt.Impl(instance()) }

    // prefs
    bind<DebtDao>() with singleton { PrefsDebtDao(instance()) }
}