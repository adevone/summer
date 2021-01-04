package io.adev.summer.example.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.setContent
import io.adev.summer.example.compose.ui.*
import io.adev.summer.example.entity.Tab
import io.adev.summer.example.presentation.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = provideViewModel(MainViewModel::class, activity = this)
        val aboutViewModel = provideViewModel(AboutViewModel::class, activity = this)
        val frameworksViewModel = provideViewModel(FrameworksViewModel::class, activity = this)
        val frameworkDetailsViewModel = provideViewModel(FrameworkDetailsViewModel::class, activity = this)
        val basketViewModel = provideViewModel(BasketViewModel::class, activity = this)
        setContent {
            MaterialTheme {
                MainUI(
                    viewModel = mainViewModel,
                    TabUI = { tab ->
                        when (tab) {
                            Tab.Frameworks -> {
                                FrameworksUI(
                                    frameworksViewModel,
                                    DetailsUI = { framework ->
                                        frameworkDetailsViewModel.init(framework)
                                        FrameworkDetailsUI(frameworkDetailsViewModel)
                                    }
                                )
                            }
                            Tab.About -> {
                                AboutUI(aboutViewModel)
                            }
                            Tab.Basket -> {
                                BasketUI(basketViewModel)
                            }
                        }
                    }
                )
            }
        }
    }
}

