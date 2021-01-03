package summer.example.presentation

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import summer.example.domain.basket.BasketController
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.Hide

interface FrameworksView {
    var items: List<Basket.Item>
    val toDetails: (framework: Framework) -> Unit
}

class FrameworksViewModel(
    private val basketController: BasketController,
    private val getAllFrameworkItems: GetAllFrameworkItems
) : BaseViewModel<FrameworksView>() {

    override val viewProxy = object : FrameworksView {
        override var items by state({ it::items }, initial = emptyList())
        override val toDetails = event { it.toDetails }.doExactlyOnce()
    }

    init {
        basketController.flow.onEach {
            updateFrameworks()
        }.launchIn(scope)
    }

    init {
        updateFrameworks()
    }

    fun onItemClick(@Hide password: String, item: Basket.Item) {
        viewProxy.toDetails(item.framework)
    }

    fun onIncreaseClick(item: Basket.Item) {
        basketController.increase(item.framework)
    }

    fun onDecreaseClick(item: Basket.Item) {
        basketController.decrease(item.framework)
    }

    private fun updateFrameworks() {
        scope.launch {
            val frameworks = getAllFrameworkItems(springVersion = "5.0")
            viewProxy.items = frameworks
        }
    }

    fun onCrashClick() {
        throw IllegalStateException("app is crashed")
    }
}