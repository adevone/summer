package summer.example.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.nullableFloat
import summer.example.domain.debt.data.DebtDao
import summer.example.entity.Debt

class PrefsDebtDao(settings: Settings) : DebtDao {

    override var debt: Debt?
        get() = debtMoney?.let { debtMoney -> Debt(debtMoney) }
        set(debt) {
            debtMoney = debt?.money
        }

    private var debtMoney by settings.nullableFloat()

    override var loan: Float? by settings.nullableFloat()
}