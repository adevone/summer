package summer

actual abstract class ArchViewModel<TView> actual constructor() :
    DefaultSummerViewModel<TView>,
    ViewProxyProvider<TView>,
    SummerViewModel<TView>()