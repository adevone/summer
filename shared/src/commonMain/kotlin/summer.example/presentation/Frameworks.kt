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
        }.launchIn(this)
    }

    override fun onEnter() {
        super.onEnter()
        updateFrameworks()
    }

    fun onFrameworkClick(@Hide password: String, framework: Framework) {
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

    fun onCrashClick() {
        throw IllegalStateException("app is crashed")
    }
}