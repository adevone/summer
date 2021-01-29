package io.adev.summer.example.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.ViewModel
import io.adev.summer.example.ViewModelFactory

@Composable
inline fun <reified VM : ViewModel> getViewModel(): VM = viewModel(factory = ViewModelFactory())