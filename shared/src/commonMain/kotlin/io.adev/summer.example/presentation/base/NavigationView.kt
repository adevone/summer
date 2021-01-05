package io.adev.summer.example.presentation.base

import io.adev.summer.example.AppNavigator
import kotlin.js.JsExport

@JsExport
interface NavigationView {
    val navigate: (navigation: (AppNavigator) -> Unit) -> Unit
}

fun <TView : NavigationView> BaseViewModel<TView>.navigationViewProxy() =
    object : NavigationView {
        override val navigate = event { it.navigate }.perform.exactlyOnce()
    }