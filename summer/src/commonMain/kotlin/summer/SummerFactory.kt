package summer

interface SummerFactory<TView> {
    fun getViewProvider(): GetViewProvider<TView>
}