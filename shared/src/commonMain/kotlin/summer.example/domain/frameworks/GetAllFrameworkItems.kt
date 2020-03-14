package summer.example.domain.frameworks

import summer.example.domain.basket.BasketHolder
import summer.example.entity.Basket

class GetAllFrameworkItems(
    private val getSpring: GetSpring,
    private val getSummer: GetSummer,
    private val basketHolder: BasketHolder
) {
    operator fun invoke(springVersion: String): List<Basket.Item> {
        val spring = getSpring(springVersion)
        val summer = getSummer()
        val frameworks = listOf(spring, summer)
        return basketHolder.basket.allAsItems(frameworks)
    }
}