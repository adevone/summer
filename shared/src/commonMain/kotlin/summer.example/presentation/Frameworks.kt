package summer.example.presentation

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import summer.example.domain.basket.BasketController
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.Hidden
import summer.example.recording.tracking

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

    val onFrameworkClick by tracking(fun(password: Hidden<String>, framework: Framework) {
        val hiddenPassword: String = password.value.toList().joinToString(separator = "") { "*" }
        println(hiddenPassword)
        viewProxy.toDetails(framework)
    })

    val onIncreaseClick by tracking(fun(framework: Framework) {
        basketController.increase(framework)
    })

    val onDecreaseClick by tracking(fun(framework: Framework) {
        basketController.decrease(framework)
    })

    private fun updateFrameworks() {
        launch {
            val frameworks = getAllFrameworkItems(springVersion = "5.0")
            viewProxy.items = frameworks
        }
    }

    val onCrashClick by tracking(fun() {
        throw IllegalStateException("app is crashed")
    })
}