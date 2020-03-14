package summer.example.presentation

import org.kodein.di.erased.instance
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.domain.shoppingCart.BasketHolder
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.base.ScreenPresenter

object FrameworksView {

    interface State {
        var items: List<Basket.Item>
    }

    interface Methods
}

interface FrameworksRouter {
    fun toDetails(framework: Framework)
}

class FrameworksPresenter : ScreenPresenter<
        FrameworksView.State,
        FrameworksView.Methods,
        FrameworksRouter>(),
    BasketHolder.Listener {

    private val basketHolder: BasketHolder by instance()
    private val getAllFrameworkItems: GetAllFrameworkItems by instance()

    override val viewStateProxy = object : FrameworksView.State {
        override var items by store({ it::items }, initial = emptyList())
    }

    override fun onAppear() {
        super.onAppear()
        basketHolder.subscribe(this)
        updateFrameworks()
    }

    override fun onDisappear() {
        super.onDisappear()
        basketHolder.unsubscribe(this)
    }

    fun onFrameworkClick(framework: Framework) {
        router.toDetails(framework)
    }

    fun onIncreaseClick(framework: Framework) {
        basketHolder.increase(framework)
    }

    fun onDecreaseClick(framework: Framework) {
        basketHolder.decrease(framework)
    }

    override fun onBasketUpdate(basket: Basket) {
        updateFrameworks()
    }

    private fun updateFrameworks() {
        val frameworks = getAllFrameworkItems(springVersion = "5.0")
        viewStateProxy.items = frameworks
    }
}