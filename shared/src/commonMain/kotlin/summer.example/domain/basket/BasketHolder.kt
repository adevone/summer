package summer.example.domain.basket

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import summer.example.entity.Basket
import summer.example.entity.Framework



interface BasketController {
    val flow: Flow<Basket>
    fun increase(framework: Framework)
    fun decrease(framework: Framework)
}

