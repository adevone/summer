package summer.example.presentation

import summer.example.domain.debt.GetDebt
import summer.example.domain.debt.data.DebtDao
import summer.example.entity.Debt
import summer.example.presentation.base.ScreenPresenter

object DebtView {

    interface State {
        var debt: Debt?
    }

    interface Methods {

    }
}

interface DebtRouter {

}

class DebtPresenter : ScreenPresenter<
        DebtView.State,
        DebtView.Methods,
        DebtRouter>() {

    private val debtDao: DebtDao by instance()

    override val viewStateProxy = object : DebtView.State {
        override var debt by store({ vs::debt }, initial = null)
    }

    private val getDebtExecutor = get<GetDebt>()
        .executor(
            onSuccess = { debt, _ ->
                viewStateProxy.debt = debt
            }
        )

    fun onCalculateDebtClick(loan: Float) {
        debtDao.loan = loan
        getDebtExecutor.execute(GetDebt.Params(isHuman = true, loan = loan))
    }
}