import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import ru.napoleonit.example.mock.MockKodeinAware
import ru.napoleonit.example.mock.instance
import ru.napoleonit.example.mock.mockModule
import summer.example.di
import summer.example.domain.debt.GetDebt
import summer.example.entity.Debt
import summer.example.presentation.DebtPresenter
import summer.example.presentation.DebtRouter
import summer.example.presentation.DebtView
import kotlin.test.assertEquals

class DebtTests : MockKodeinAware {

    @Test
    fun `is debt money for human valid`() = runBlocking {
        val getDebt = GetDebt.Impl(instance())
        val debt = getDebt(GetDebt.Params(isHuman = true, loan = 1f))
        assertEquals(100.500f, debt.money)
    }

    @Test
    fun `is debt money for non-human valid`() = runBlocking {
        val getDebt = GetDebt.Impl(instance())
        val debt = getDebt(GetDebt.Params(isHuman = false, loan = 1f))
        assertEquals(0f, debt.money)
    }

    @Test
    fun `user clicks for calculate debt`() {

        val userMoney = 1f

        di = Kodein {
            import(mockModule)
            bind<GetDebt>() with singleton {
                object : GetDebt {
                    override suspend fun invoke(params: GetDebt.Params): Debt {
                        return Debt(money = userMoney)
                    }
                }
            }
        }

        val debtPresenter = DebtPresenter()

        val state = object : DebtView.State {
            override var debt: Debt? = null
        }

        debtPresenter.viewCreated(
            viewState = state,
            viewMethods = object : DebtView.Methods {},
            router = object : DebtRouter {}
        )

        debtPresenter.onCalculateDebtClick(loan = 1f)

        assertEquals(userMoney, state.debt?.money)
    }
}