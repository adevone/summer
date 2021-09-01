package io.adev.summer.example.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.adev.summer.example.ViewModelFactory

@Composable
inline fun <reified VM : ViewModel> getViewModel(): VM = viewModel(factory = ViewModelFactory())