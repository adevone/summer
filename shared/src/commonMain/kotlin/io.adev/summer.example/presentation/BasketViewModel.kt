package io.adev.summer.example.presentation

import io.adev.summer.example.domain.basket.BasketController
import io.adev.summer.example.entity.BasketItem
import io.adev.summer.example.presentation.base.BaseInput
import io.adev.summer.example.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.js.JsExport

@JsExport
interface BasketView {
    var items: Array<BasketItem>
}

@JsExport
interface BasketInput : BaseInput<BasketView>

class BasketViewModel(
    basketController: BasketController
) : BaseViewModel<BasketView>(), BasketInput {

    override val viewProxy = object : BasketView {
        override var items by state({ it::items }, initial = emptyArray())
    }

    init {
        basketController.flow.onEach { basket ->
            viewProxy.items = basket.items.toTypedArray()
        }.launchIn(scope)
    }
}