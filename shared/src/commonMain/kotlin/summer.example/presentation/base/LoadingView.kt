package summer.example.presentation.base

interface LoadingView {
    var isLoading: Boolean
}

inline fun BasePresenter<out LoadingView>.withLoading(action: () -> Unit) {
    viewProxy.isLoading = true
    try {
        action()
    } finally {
        viewProxy.isLoading = false
    }
}