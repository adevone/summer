package summer.example.domain.basket

import kotlinx.coroutines.flow.Flow
import summer.example.entity.Basket
import summer.example.entity.Framework

interface BasketController {
    val flow: Flow<Basket>
    fun increase(framework: Framework)
    fun decrease(framework: Framework)
}

