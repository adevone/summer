package io.adev.summer.example.domain.frameworks

import io.adev.summer.example.domain.basket.BasketController
import io.adev.summer.example.entity.BasketItem
import kotlinx.coroutines.flow.first

class GetAllFrameworkItems(
    private val getSpring: GetSpring,
    private val getSummer: GetSummer,
    private val basketHolder: BasketController,
) {
    suspend operator fun invoke(springVersion: String): List<BasketItem> {
        val spring = getSpring(springVersion)
        val summer = getSummer()
        val frameworks = listOf(spring, summer)
        val basket = basketHolder.flow.first()
        return basket.allAsItems(frameworks)
    }
}