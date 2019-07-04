package ru.napoleonit.example.domain.debt.data

import ru.napoleonit.example.entity.Debt

interface DebtDao {
    var debt: Debt?
    var loan: Float?
}