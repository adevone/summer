package io.adev.summer.example.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.adev.summer.example.compose.bind
import io.adev.summer.example.compose.getViewModel
import io.adev.summer.example.entity.Framework
import io.adev.summer.example.entity.FullFramework
import io.adev.summer.example.presentation.FrameworkDetailsView
import io.adev.summer.example.presentation.FrameworkDetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun FrameworkDetailsUI(
    initialFramework: Framework,
    scaffoldState: ScaffoldState,
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = getViewModel<FrameworkDetailsViewModel>()
    val view = viewModel.bind(object : FrameworkDetailsView {
        override var framework: FullFramework? by remember { mutableStateOf(null) }
        override val notifyAboutName: (String) -> Unit = { frameworkName ->
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(frameworkName)
            }
        }
    })
    LaunchedEffect(Unit) {
        viewModel.init(initialFramework)
    }
    view.framework?.let { framework ->
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(text = framework.name, fontSize = 22.sp)
            Text(text = framework.version, fontSize = 16.sp)
        }
    }
}