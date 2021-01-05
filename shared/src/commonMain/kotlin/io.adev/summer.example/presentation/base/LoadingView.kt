package io.adev.summer.example.presentation.base

import kotlin.js.JsExport

@JsExport
interface LoadingView {
    var isLoading: Boolean
}

inline fun BaseViewModel<out LoadingView>.withLoading(action: () -> Unit) {
    viewProxy.isLoading = true
    try {
        action()
    } finally {
        viewProxy.isLoading = false
    }
}

fun BaseViewModel<out LoadingView>.loadingViewProxy() = object : LoadingView {
    override var isLoading by state({ it::isLoading }, initial = false)
}