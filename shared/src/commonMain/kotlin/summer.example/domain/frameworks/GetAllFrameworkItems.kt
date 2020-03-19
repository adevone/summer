package summer.example.domain.frameworks

import kotlinx.coroutines.flow.first
import summer.example.domain.basket.BasketController
import summer.example.entity.Basket

class GetAllFrameworkItems(
    private val getSpring: GetSpring,
    private val getSummer: GetSummer,
    private val basketHolder: BasketController
) {
    suspend operator fun invoke(springVersion: String): List<Basket.Item> {
        val spring = getSpring(springVersion)
        val summer = getSummer()
        val frameworks = listOf(spring, summer)
        val basket = basketHolder.flow.first()
        return basket.allAsItems(frameworks)
    }
}