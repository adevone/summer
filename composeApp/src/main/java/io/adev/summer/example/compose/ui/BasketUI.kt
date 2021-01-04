package io.adev.summer.example.compose.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.adev.summer.example.compose.bind
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.presentation.BasketView
import io.adev.summer.example.presentation.BasketViewModel

@Composable
fun BasketUI(viewModel: BasketViewModel) {
    val view = viewModel.bind(object : BasketView {
        override var items: List<Basket.Item> by mutableStateOf(emptyList())
    })
    LazyColumn(content = {
        items(view.items) { item ->
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = item.framework.name)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = item.quantity.toString())
            }
        }
    })
}