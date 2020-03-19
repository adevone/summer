package summer.example.domain.basket

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import summer.example.entity.Basket
import summer.example.entity.Framework

class CoroutinesBasketController : BasketController {
    private val channel = ConflatedBroadcastChannel(value = Basket(items = emptyList()))
    override val flow: Flow<Basket> = channel.asFlow()

    override fun increase(framework: Framework) {
        val previous = channel.value
        val increased = previous.withIncreased(framework)
        channel.offer(increased)
    }

    override fun decrease(framework: Framework) {
        val previous = channel.value
        val decreased = previous.withDecreased(framework)
        channel.offer(decreased)
    }
}