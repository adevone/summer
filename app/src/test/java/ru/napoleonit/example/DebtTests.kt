import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import summer.example.di
import ru.napoleonit.example.domain.debt.GetDebt
import ru.napoleonit.example.entity.Debt
import ru.napoleonit.example.mock.MockKodeinAware
import ru.napoleonit.example.mock.instance
import ru.napoleonit.example.mock.mockModule
import ru.napoleonit.example.mock.mockScope
import ru.napoleonit.example.presentation.DebtPresenter
import ru.napoleonit.example.presentation.DebtRouter
import ru.napoleonit.example.presentation.DebtView
import kotlin.test.assertEquals

class DebtTests : MockKodeinAware {

    @Test
    fun `is debt money for human valid`() = runBlocking {
        val getDebt = GetDebt.Impl(instance())
        val debt = getDebt.execute(GetDebt.Params(isHuman = true, loan = 1f), mockScope).await()
        assertEquals(100.500f, debt.money)
    }

    @Test
    fun `is debt money for non-human valid`() = runBlocking {
        val getDebt = GetDebt.Impl(instance())
        val debt = getDebt.execute(GetDebt.Params(isHuman = false, loan = 1f), mockScope).await()
        assertEquals(0f, debt.money)
    }

    @Test
    fun `user clicks for calculate debt`() {

        val userMoney = 1f

        di = Kodein {
            import(mockModule)
            bind<GetDebt>() with singleton {
                object : GetDebt {
                    override fun execute(
                        params: GetDebt.Params,
                        scope: CoroutineScope
                    ) = scope.async {
                        Debt(money = userMoney)
                    }
                }
            }
        }

        val debtPresenter = DebtPresenter()

        val state = object : DebtView.State {
            override var debt: Debt? = null
        }

        debtPresenter.onCreateView(
            viewState = state,
            viewMethods = object : DebtView.Methods {},
            router = object : DebtRouter {},
            owner = Unit
        )

        debtPresenter.onCalculateDebtClick(loan = 1f)

        assertEquals(userMoney, state.debt?.money)
    }
}