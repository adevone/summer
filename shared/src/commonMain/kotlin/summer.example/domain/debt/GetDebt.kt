package ru.napoleonit.example.domain.debt

import ru.napoleonit.example.domain.UseCase
import ru.napoleonit.example.domain.debt.data.DebtDao
import ru.napoleonit.example.entity.Debt

interface GetDebt : UseCase<Debt, GetDebt.Params> {

    class Impl(
        private val debtDao: DebtDao
    ) : GetDebt {

        override suspend fun execute(params: Params): Debt {

            val debt = Debt(
                money = if (params.isHuman)
                    100.500f * params.loan
                else
                    0f
            )

            debtDao.debt = debt

            return debt
        }
    }

    data class Params(
        val loan: Float,
        val isHuman: Boolean
    )
}