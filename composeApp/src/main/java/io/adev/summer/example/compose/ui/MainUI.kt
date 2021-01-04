package io.adev.summer.example.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import io.adev.summer.example.compose.R
import io.adev.summer.example.compose.bind
import io.adev.summer.example.compose.getViewModel
import io.adev.summer.example.entity.Tab
import io.adev.summer.example.presentation.MainView
import io.adev.summer.example.presentation.MainViewModel

@Composable
fun MainUI() {
    val viewModel = getViewModel<MainViewModel>()
    val view = viewModel.bind(object : MainView {
        override var tabs: List<Tab> by mutableStateOf(emptyList())
        override var selectedTab: Tab? by mutableStateOf(null)
    })
    Scaffold(bottomBar = {
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
    }) {
        val selectedTab = view.selectedTab
        if (selectedTab != null) {
            when (selectedTab) {
                Tab.Frameworks -> {
                    FrameworksUI()
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
}

@Composable
fun TabImage(tab: Tab) {
    val imageId = when (tab) {
        Tab.Frameworks -> R.drawable.menu_frameworks
        Tab.About -> R.drawable.menu_about
        Tab.Basket -> R.drawable.menu_basket
    }
    Image(imageVector = vectorResource(id = imageId))
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