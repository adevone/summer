package summer.example.domain.basket

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import summer.example.entity.Basket
import summer.example.entity.Framework

class CoroutinesBasketController : BasketController {
    private val _flow = MutableStateFlow(value = Basket(items = emptyList()))
    override val flow: Flow<Basket> = _flow

    override fun increase(framework: Framework) {
        val previous = _flow.value
        val increased = previous.withIncreased(framework)
        _flow.value = increased
    }

    override fun decrease(framework: Framework) {
        val previous = _flow.value
        val decreased = previous.withDecreased(framework)
        _flow.value = decreased
    }
}