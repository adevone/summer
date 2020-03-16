package summer.example.presentation

import org.kodein.di.erased.instance
import summer.example.domain.basket.BasketHolder
import summer.example.entity.Basket
import summer.example.presentation.base.ScreenPresenter

interface BasketView {
    var items: List<Basket.Item>
}

interface BasketRouter {

}

class BasketPresenter : ScreenPresenter<BasketView>(), BasketHolder.Listener {

    private val basketHolder: BasketHolder by instance()

    override val viewProxy = object : BasketView {
        override var items by store({ it::items }, initial = emptyList())
    }

    override fun onAppear() {
        super.onAppear()
        basketHolder.subscribe(listener = this)
    }

    override fun onDisappear() {
        super.onDisappear()
        basketHolder.unsubscribe(listener = this)
    }

    override fun onBasketUpdate(basket: Basket) {
        viewProxy.items = basket.items
    }
}