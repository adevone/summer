package summer.example.presentation

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kodein.di.instance
import summer.example.domain.basket.BasketController
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.base.BaseViewModel

interface FrameworksView {
    var items: List<Basket.Item>
    val toDetails: (framework: Framework) -> Unit
}

class FrameworksViewModel : BaseViewModel<FrameworksView>() {
    private val basketController: BasketController by instance()
    private val getAllFrameworkItems: GetAllFrameworkItems by instance()

    override val viewProxy = object : FrameworksView {
        override var items by state({ it::items }, initial = emptyList())
        override val toDetails = event { it.toDetails }.doExactlyOnce()
    }

    init {
        basketController.flow.onEach {
            updateFrameworks()
        }.launchIn(viewModelScope)
    }

    override fun onEnter() {
        super.onEnter()
        updateFrameworks()
    }

    fun onItemClick(item: Basket.Item) {
        viewProxy.toDetails(item.framework)
    }

    fun onIncreaseClick(item: Basket.Item) {
        basketController.increase(item.framework)
    }

    fun onDecreaseClick(item: Basket.Item) {
        basketController.decrease(item.framework)
    }

    private fun updateFrameworks() {
        viewModelScope.launch {
            val frameworks = getAllFrameworkItems(springVersion = "5.0")
            viewProxy.items = frameworks
        }
    }
}