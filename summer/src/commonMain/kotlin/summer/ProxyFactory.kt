package summer

interface ProxyFactory<TView> {
    fun getViewProvider(): GetViewProvider<TView>
}