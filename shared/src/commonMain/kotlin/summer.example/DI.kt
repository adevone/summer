package summer.example

import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import ru.napoleonit.example.data.PrefsDebtDao
import ru.napoleonit.example.domain.debt.GetDebt
import ru.napoleonit.example.domain.debt.data.DebtDao
import summer.summer.StateHolder

lateinit var di: Kodein

val sharedModule = Kodein.Module("shared") {

    // domain
    bind<GetDebt>() with singleton { GetDebt.Impl(instance()) }

    // prefs
    bind<DebtDao>() with singleton { PrefsDebtDao(instance()) }

    // presentation
    bind() from singleton { StateHolder() }
}