package summer.example.presentation

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kodein.di.erased.instance
import summer.example.domain.basket.BasketController
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.base.BasePresenter

interface FrameworksView {
    var items: List<Basket.Item>
    val toDetails: (framework: Framework) -> Unit
}

class FrameworksPresenter : BasePresenter<FrameworksView>() {
    private val basketController: BasketController by instance()
    private val getAllFrameworkItems: GetAllFrameworkItems by instance()

    override val viewProxy = object : FrameworksView {
        override var items by state({ it::items }, initial = emptyList())
        override val toDetails = event { it.toDetails }.doExactlyOnce()
    }

    init {
        basketController.flow.onEach {
            updateFrameworks()
        }.launchIn(this)
    }

    override fun onEnter() {
        super.onEnter()
        updateFrameworks()
    }

    fun onFrameworkClick(framework: Framework) {
        viewProxy.toDetails(framework)
    }

    fun onIncreaseClick(framework: Framework) {
        basketController.increase(framework)
    }

    fun onDecreaseClick(framework: Framework) {
        basketController.decrease(framework)
    }

    private fun updateFrameworks() {
        launch {
            val frameworks = getAllFrameworkItems(springVersion = "5.0")
            viewProxy.items = frameworks
        }
    }
}