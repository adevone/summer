package summer.example.presentation

import org.kodein.di.erased.instance
import summer.example.domain.shoppingCart.BasketHolder
import summer.example.entity.Basket
import summer.example.presentation.base.ScreenPresenter

object ShoppingCartView {

    interface State {
        var items: List<Basket.Item>
    }

    interface Methods {

    }
}

interface ShoppingCartRouter {

}

class ShoppingCartPresenter : ScreenPresenter<
        ShoppingCartView.State,
        ShoppingCartView.Methods,
        ShoppingCartRouter>(),
    BasketHolder.Listener {

    private val basketHolder: BasketHolder by instance()

    override val viewStateProxy = object : ShoppingCartView.State {
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