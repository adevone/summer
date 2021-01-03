package io.adev.summer.example.domain.frameworks

import io.adev.summer.example.domain.basket.BasketController
import kotlinx.coroutines.flow.first
import io.adev.summer.example.entity.Basket

class GetAllFrameworkItems(
    private val getSpring: GetSpring,
    private val getSummer: GetSummer,
    private val basketHolder: BasketController,
) {
    suspend operator fun invoke(springVersion: String): List<Basket.Item> {
        val spring = getSpring(springVersion)
        val summer = getSummer()
        val frameworks = listOf(spring, summer)
        val basket = basketHolder.flow.first()
        return basket.allAsItems(frameworks)
    }
}