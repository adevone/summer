package summer.example.debt

import kotlinx.android.synthetic.main.debt_fragment.*
import summer.example.R
import summer.example.ScreenFragment
import summer.example.entity.Debt
import summer.example.presentation.DebtPresenter
import summer.example.presentation.DebtRouter
import summer.example.presentation.DebtView

class DebtFragment : ScreenFragment<
        DebtView.State,
        DebtView.Methods,
        DebtRouter,
        DebtPresenter>() {

    override val router = object : DebtRouter {

    }

    override fun createViewState() = object : DebtView.State {

        override var debt: Debt? = null
            set(debt) {
                val money = debt?.money ?: 0f
                debtView.text = getString(R.string.debt, money)
                field = debt
            }
    }

    override val viewMethods = object : DebtView.Methods {

    }

    override fun createPresenter() = DebtPresenter()

    override val layoutRes = R.layout.debt_fragment

    override fun initView() {
        calculateButton.setOnClickListener {
            val loan = loanEdit.text?.toString()?.toFloatOrNull() ?: 0f
            presenter.onCalculateDebtClick(loan)
        }
    }
}