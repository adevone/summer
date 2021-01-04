package io.adev.summer.example.compose.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.adev.summer.example.compose.App
import io.adev.summer.example.compose.bind
import io.adev.summer.example.compose.getViewModel
import io.adev.summer.example.entity.Framework
import io.adev.summer.example.entity.FullFramework
import io.adev.summer.example.presentation.FrameworkDetailsView
import io.adev.summer.example.presentation.FrameworkDetailsViewModel

@Composable
fun FrameworkDetailsUI(initialFramework: Framework) {
    val viewModel = getViewModel<FrameworkDetailsViewModel>()
    val view = viewModel.bind(object : FrameworkDetailsView {
        override var framework: FullFramework? by mutableStateOf(null)
        override val notifyAboutName: (String) -> Unit = { frameworkName ->
            Toast.makeText(App.instance, frameworkName, Toast.LENGTH_SHORT).show()
        }
    })
    viewModel.init(initialFramework)
    view.framework?.let { framework ->
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(text = framework.name, fontSize = 22.sp)
            Text(text = framework.version, fontSize = 16.sp)
        }
    }
}