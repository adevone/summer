package summer.example.presentation.base

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

fun <TView : LoadingView> BaseViewModel<TView>.loadingViewProxy() =
    object : LoadingView {
        override var isLoading by state({ it::isLoading }, initial = false)
    }