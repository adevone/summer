package summer

interface ViewProvider<out TView> {
    val getView: () -> TView?
}