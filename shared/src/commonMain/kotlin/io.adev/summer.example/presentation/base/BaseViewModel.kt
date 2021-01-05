package io.adev.summer.example.presentation.base

import kotlinx.coroutines.CoroutineScope
import summer.ArchViewModel
import kotlin.js.JsExport

expect abstract class BaseViewModel<TView>() : ArchViewModel<TView>, BaseInput<TView> {
    val scope: CoroutineScope
}

@JsExport
interface BaseInput<TView> {
    var getView: () -> TView?
    fun viewCreated()
}