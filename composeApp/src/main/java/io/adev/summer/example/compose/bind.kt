package io.adev.summer.example.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AmbientLifecycleOwner
import summer.ViewModelBinder

@Composable
fun <TView> ViewModelBinder<TView>.bind(view: TView): TView {
    bindView(AmbientLifecycleOwner.current, { view })
    return view
}