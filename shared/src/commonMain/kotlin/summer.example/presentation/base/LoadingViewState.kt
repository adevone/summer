package summer.example.presentation.base

interface LoadingViewState {
    var isLoading: Boolean
}

inline fun ScreenPresenter<out LoadingViewState, *, *>.withLoading(action: () -> Unit) {
    viewStateProxy.isLoading = true
    try {
        action()
    } finally {
        viewStateProxy.isLoading = false
    }
}