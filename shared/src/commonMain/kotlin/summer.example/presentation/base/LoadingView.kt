package summer.example.presentation.base

interface LoadingView {
    var isLoading: Boolean
}

inline fun ScreenPresenter<out LoadingView>.withLoading(action: () -> Unit) {
    viewProxy.isLoading = true
    try {
        action()
    } finally {
        viewProxy.isLoading = false
    }
}