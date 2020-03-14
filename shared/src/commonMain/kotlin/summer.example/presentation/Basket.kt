package summer.example.presentation

import org.kodein.di.erased.instance
import summer.example.domain.basket.BasketHolder
import summer.example.entity.Basket
import summer.example.presentation.base.ScreenPresenter

object BasketView {

    interface State {
        var items: List<Basket.Item>
    }

    interface Methods {

    }
}

interface BasketRouter {

}

class BasketPresenter : ScreenPresenter<
        BasketView.State,
        BasketView.Methods,
        BasketRouter>(),
    BasketHolder.Listener {

    private val basketHolder: BasketHolder by instance()

    override val viewStateProxy = object : BasketView.State {
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
        viewStateProxy.items = basket.items
    }
}