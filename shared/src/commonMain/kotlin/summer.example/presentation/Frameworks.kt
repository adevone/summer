package summer.example.presentation

import org.kodein.di.erased.instance
import summer.example.domain.basket.BasketHolder
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.base.ScreenPresenter

interface FrameworksView {
    var items: List<Basket.Item>
    val toDetails: (framework: Framework) -> Unit
}

interface FrameworksRouter {
    fun toDetails(framework: Framework)
}

class FrameworksPresenter : ScreenPresenter<FrameworksView>(), BasketHolder.Listener {

    private val basketHolder: BasketHolder by instance()
    private val getAllFrameworkItems: GetAllFrameworkItems by instance()

    override val viewProxy = object : FrameworksView {
        override var items by store({ it::items }, initial = emptyList())
        override val toDetails = event { it.toDetails }.doExactlyOnce()
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
        viewProxy.toDetails(framework)
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
        viewProxy.items = frameworks
    }
}