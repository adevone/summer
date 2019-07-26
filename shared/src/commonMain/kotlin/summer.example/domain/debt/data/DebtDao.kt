package summer.example.domain.debt.data

import summer.example.entity.Debt

interface DebtDao {
    var debt: Debt?
    var loan: Float?
}