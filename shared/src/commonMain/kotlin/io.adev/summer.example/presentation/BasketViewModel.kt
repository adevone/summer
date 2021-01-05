package io.adev.summer.example.presentation

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import io.adev.summer.example.domain.basket.BasketController
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.presentation.base.BaseViewModel

interface BasketView {
    var items: List<Basket.Item>
}

class BasketViewModel(basketController: BasketController) : BaseViewModel<BasketView>() {

    override val viewProxy = object : BasketView {
        override var items by state({ it::items }, initial = emptyList())
    }

    init {
        basketController.flow.onEach { basket ->
            viewProxy.items = basket.items
        }.launchIn(scope)
    }
}