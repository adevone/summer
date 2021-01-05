package io.adev.summer.example.presentation

import io.adev.summer.example.domain.basket.BasketController
import io.adev.summer.example.domain.frameworks.GetAllFrameworkItems
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.entity.BasketItem
import io.adev.summer.example.presentation.base.BaseInput
import io.adev.summer.example.presentation.base.BaseViewModel
import io.adev.summer.example.presentation.base.NavigationView
import io.adev.summer.example.presentation.base.navigationViewProxy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.js.JsExport

@JsExport
interface FrameworksView : NavigationView {
    var items: Array<BasketItem>
}

@JsExport
interface FrameworksInput : BaseInput<FrameworksView> {
    fun onItemClick(item: BasketItem)
    fun onIncreaseClick(item: BasketItem)
    fun onDecreaseClick(item: BasketItem)
}

class FrameworksViewModel(
    private val basketController: BasketController,
    private val getAllFrameworkItems: GetAllFrameworkItems,
) : BaseViewModel<FrameworksView>(), FrameworksInput {

    override val viewProxy: FrameworksView = object : FrameworksView,
        NavigationView by navigationViewProxy() {
        override var items by state({ it::items }, initial = emptyArray())
    }

    init {
        basketController.flow.onEach {
            updateFrameworks()
        }.launchIn(scope)
    }

    init {
        updateFrameworks()
    }

    override fun onItemClick(item: BasketItem) {
        viewProxy.navigate { it.toFrameworkDetails(item.framework) }
    }

    override fun onIncreaseClick(item: BasketItem) {
        basketController.increase(item.framework)
    }

    override fun onDecreaseClick(item: BasketItem) {
        basketController.decrease(item.framework)
    }

    private fun updateFrameworks() {
        scope.launch {
            val frameworks = getAllFrameworkItems(springVersion = "5.0")
            viewProxy.items = frameworks.toTypedArray()
        }
    }
}