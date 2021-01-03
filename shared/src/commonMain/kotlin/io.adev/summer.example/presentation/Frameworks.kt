package io.adev.summer.example.presentation

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kodein.di.instance
import io.adev.summer.example.domain.basket.BasketController
import io.adev.summer.example.domain.frameworks.GetAllFrameworkItems
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.entity.Framework
import io.adev.summer.example.presentation.base.BaseViewModel

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
        }.launchIn(scope)
    }

    init {
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
        scope.launch {
            val frameworks = getAllFrameworkItems(springVersion = "5.0")
            viewProxy.items = frameworks
        }
    }
}