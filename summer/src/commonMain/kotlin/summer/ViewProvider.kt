package summer

interface ViewProvider<TView> {
    fun getView(): TView
}