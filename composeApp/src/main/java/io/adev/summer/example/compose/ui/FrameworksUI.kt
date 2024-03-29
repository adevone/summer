package io.adev.summer.example.compose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.adev.summer.example.compose.bind
import io.adev.summer.example.compose.getViewModel
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.presentation.FrameworksView
import io.adev.summer.example.presentation.FrameworksViewModel
import io.adev.summer.example.presentation.base.NavigationView

@Composable
fun FrameworksUI(navigationView: NavigationView) {
    val viewModel = getViewModel<FrameworksViewModel>()
    val view = viewModel.bind(object : FrameworksView, NavigationView by navigationView {
        override var items: List<Basket.Item> by remember { mutableStateOf(emptyList()) }
    })
    LazyColumn(content = {
        items(view.items) { item ->
            Row(
                modifier = Modifier
                    .clickable(
                        onClick = {
                            viewModel.onItemClick(item)
                        }
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = item.framework.name)
                    Text(text = item.framework.version)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    viewModel.onDecreaseClick(item)
                }) {
                    Text(text = "-")
                }
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = item.quantity.toString()
                )
                Button(onClick = {
                    viewModel.onIncreaseClick(item)
                }) {
                    Text(text = "+")
                }
            }
            Divider()
        }
    })
}