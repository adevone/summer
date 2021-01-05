package io.adev.summer.example.presentation

import io.adev.summer.example.domain.basket.BasketController
import io.adev.summer.example.domain.frameworks.GetAllFrameworkItems
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.presentation.base.BaseViewModel
import io.adev.summer.example.presentation.base.NavigationView
import io.adev.summer.example.presentation.base.navigationViewProxy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kodein.di.instance

interface FrameworksView : NavigationView {
    var items: List<Basket.Item>
}

class FrameworksViewModel : BaseViewModel<FrameworksView>() {
    private val basketController: BasketController by instance()
    private val getAllFrameworkItems: GetAllFrameworkItems by instance()

    override val viewProxy: FrameworksView = object : FrameworksView,
        NavigationView by navigationViewProxy() {
        override var items by state({ it::items }, initial = emptyList())
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
        viewProxy.navigate { it.toFrameworkDetails(item.framework) }
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