package io.adev.summer.example.compose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import io.adev.summer.example.compose.bind
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.entity.Framework
import io.adev.summer.example.presentation.FrameworksView
import io.adev.summer.example.presentation.FrameworksViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private enum class Destination {
    Main,
    Details
}

@Composable
fun FrameworksUI(
    viewModel: FrameworksViewModel,
    DetailsUI: @Composable (Framework) -> Unit,
) {
    val navController = rememberNavController()
    val view = viewModel.bind(object : FrameworksView {
        override var items: List<Basket.Item> by mutableStateOf(emptyList())
        override val toDetails: (Framework) -> Unit = { framework ->
            val frameworkString = Json.encodeToString(framework)
            navController.navigate("${Destination.Details.name}/$frameworkString")
        }
    })
    NavHost(navController, startDestination = Destination.Main.name) {
        composable(Destination.Main.name) {
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
        composable(
            "${Destination.Details.name}/{framework}",
            arguments = listOf(navArgument("framework") {
                this.type = NavType.StringType
            })
        ) { backStackEntry ->
            val frameworkString = backStackEntry.arguments?.getString("framework")!!
            val framework = Json.decodeFromString(Framework.serializer(), frameworkString)
            DetailsUI(framework)
        }
    }
}