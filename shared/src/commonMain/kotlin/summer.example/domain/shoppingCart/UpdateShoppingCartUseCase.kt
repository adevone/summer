package summer.example.domain.shoppingCart

import summer.example.domain.EventsEmitter
import summer.example.entity.Basket
import summer.example.entity.Framework

class BasketHolder : EventsEmitter<BasketHolder.Listener>() {

    var basket = Basket(items = emptyList())

    fun increase(framework: Framework) {
        val increased = basket.withIncreased(framework)
        basket = increased
        listener?.onBasketUpdate(basket = increased)
    }

    fun decrease(framework: Framework) {
        val decreased = basket.withDecreased(framework)
        basket = decreased
        listener?.onBasketUpdate(basket = decreased)
    }

    override fun subscribe(listener: Listener) {
        super.subscribe(listener)
        listener.onBasketUpdate(basket)
    }

    interface Listener {
        fun onBasketUpdate(basket: Basket)
    }
}

