package summer

interface ViewProvider<TView> {
    val getView: () -> TView?
}