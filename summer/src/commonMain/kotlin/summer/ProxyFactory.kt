package summer

interface ProxyFactory<TView> {
    fun getViewProvider(): ViewStateProvider<TView>
}