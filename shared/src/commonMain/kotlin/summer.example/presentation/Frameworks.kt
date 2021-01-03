package summer.example.presentation

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import summer.example.domain.basket.BasketController
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.Hidden
import summer.example.presentation.base.exhaustive

interface FrameworksView {
    var items: List<Basket.Item>
    val toDetails: (framework: Framework) -> Unit
}

sealed class FrameworksInput {

    @Serializable
    data class ItemClicked(val password: Hidden<String>, val item: Basket.Item) : FrameworksInput()

    @Serializable
    data class IncreaseClicked(val item: Basket.Item) : FrameworksInput()

    @Serializable
    data class DecreaseClicked(val item: Basket.Item) : FrameworksInput()

    @Serializable
    object CrashClicked : FrameworksInput()
}

class FrameworksViewModel(
    private val basketController: BasketController,
    private val getAllFrameworkItems: GetAllFrameworkItems,
) : BaseViewModel<FrameworksView, FrameworksInput>() {

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

    private fun updateFrameworks() {
        scope.launch {
            val frameworks = getAllFrameworkItems(springVersion = "5.0")
            viewProxy.items = frameworks
        }
    }

    override fun handle(input: FrameworksInput) {
        when (input) {
            is FrameworksInput.ItemClicked -> {
                viewProxy.toDetails(input.item.framework)
            }
            is FrameworksInput.IncreaseClicked -> {
                basketController.increase(input.item.framework)
            }
            is FrameworksInput.DecreaseClicked -> {
                basketController.decrease(input.item.framework)
            }
            FrameworksInput.CrashClicked -> {
                throw IllegalStateException("app is crashed")
            }
        }.exhaustive
    }
}