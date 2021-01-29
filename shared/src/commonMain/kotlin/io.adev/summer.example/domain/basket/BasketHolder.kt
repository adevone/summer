package io.adev.summer.example.domain.basket

import kotlinx.coroutines.flow.Flow
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.entity.Framework

interface BasketController {
    val flow: Flow<Basket>
    fun increase(framework: Framework)
    fun decrease(framework: Framework)
}

