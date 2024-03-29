package io.adev.summer.example.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.adev.summer.example.AppNavigator
import io.adev.summer.example.compose.R
import io.adev.summer.example.compose.bind
import io.adev.summer.example.compose.getViewModel
import io.adev.summer.example.entity.Framework
import io.adev.summer.example.entity.Tab
import io.adev.summer.example.presentation.MainView
import io.adev.summer.example.presentation.MainViewModel
import io.adev.summer.example.presentation.base.NavigationView
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private enum class Destination {
    Main,
    Details
}

@Composable
fun MainUI() {
    val viewModel = getViewModel<MainViewModel>()
    val navController = rememberNavController()
    val navigator = object : AppNavigator {
        override fun toFrameworkDetails(framework: Framework) {
            val frameworkString = Json.encodeToString(framework)
            navController.navigate("${Destination.Details.name}/$frameworkString")
        }
    }
    val navigationView = object : NavigationView {
        override val navigate: ((AppNavigator) -> Unit) -> Unit = { navigation ->
            navigation(navigator)
        }
    }
    val view = viewModel.bind(object : MainView {
        override var tabs: List<Tab> by remember { mutableStateOf(emptyList()) }
        override var selectedTab: Tab? by remember { mutableStateOf(null) }
    })
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigation {
                view.tabs.forEach { tab ->
                    BottomNavigationItem(
                        icon = {
                            TabImage(tab)
                        },
                        label = {
                            TabLabel(tab)
                        },
                        selected = view.selectedTab == tab,
                        onClick = {
                            viewModel.onMenuItemClick(tab)
                        }
                    )
                }
            }
        },
    ) { padding ->
        NavHost(
            navController,
            startDestination = Destination.Main.name,
            modifier = Modifier.padding(padding)
        ) {
            composable(Destination.Main.name) {
                val selectedTab = view.selectedTab
                if (selectedTab != null) {
                    when (selectedTab) {
                        Tab.Frameworks -> {
                            FrameworksUI(navigationView)
                        }
                        Tab.About -> {
                            AboutUI()
                        }
                        Tab.Basket -> {
                            BasketUI()
                        }
                    }
                } else {
                    CircularProgressIndicator()
                }
            }
            composable(
                "${Destination.Details.name}/{framework}",
                arguments = listOf(navArgument("framework") {
                    this.type = NavType.StringType
                })
            ) { backStackEntry ->
                val frameworkString = backStackEntry.arguments?.getString("framework")!!
                val framework = Json.decodeFromString(Framework.serializer(), frameworkString)
                FrameworkDetailsUI(initialFramework = framework, scaffoldState)
            }
        }
    }
}

@Composable
fun TabImage(tab: Tab) {
    val imageId = when (tab) {
        Tab.Frameworks -> R.drawable.menu_frameworks
        Tab.About -> R.drawable.menu_about
        Tab.Basket -> R.drawable.menu_basket
    }
    Image(
        painter = painterResource(imageId),
        contentDescription = null,
    )
}

@Composable
fun TabLabel(tab: Tab) {
    val textId = when (tab) {
        Tab.Frameworks -> R.string.menu_frameworks
        Tab.About -> R.string.menu_about
        Tab.Basket -> R.string.menu_basket
    }
    Text(text = stringResource(textId))
}