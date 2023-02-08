package io.adev.summer.example.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import summer.arch.ViewModelBinder

@Composable
fun <TView> ViewModelBinder<TView>.bind(view: TView): TView {
    bindView(LocalLifecycleOwner.current) { view }
    return view
}