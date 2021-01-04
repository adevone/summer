package io.adev.summer.example.compose.ui

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
import io.adev.summer.example.compose.bind
import io.adev.summer.example.entity.FullFramework
import io.adev.summer.example.presentation.FrameworkDetailsView
import io.adev.summer.example.presentation.FrameworkDetailsViewModel

@Composable
fun FrameworkDetailsUI(viewModel: FrameworkDetailsViewModel) {
    val view = viewModel.bind(object : FrameworkDetailsView {
        override var framework: FullFramework? by mutableStateOf(null)
        override val notifyAboutName: (String) -> Unit = { frameworkName ->
//            Toast.makeText(AmbientContext.current, frameworkName, Toast.LENGTH_SHORT).show()
        }
    })
    view.framework?.let { framework ->
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(text = framework.name, fontSize = 22.sp)
            Text(text = framework.version, fontSize = 16.sp)
        }
    }
}