package io.adev.summer.example.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.adev.summer.example.compose.GlideImage
import io.adev.summer.example.compose.bind
import io.adev.summer.example.compose.getViewModel
import io.adev.summer.example.entity.About
import io.adev.summer.example.presentation.AboutView
import io.adev.summer.example.presentation.AboutViewModel

@Composable
fun AboutUI() {
    val viewModel = getViewModel<AboutViewModel>()
    val view = viewModel.bind(object : AboutView {
        override var about: About? by remember { mutableStateOf(null) }
        override var isLoading: Boolean by remember { mutableStateOf(false) }
    })
    val about = view.about
    if (about != null) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxSize()
        ) {
            Text(text = about.author, fontSize = 25.sp)
            Text(text = about.frameworkName, fontSize = 20.sp)
            GlideImage(url = about.logoUrl)
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            CircularProgressIndicator()
        }
    }
}