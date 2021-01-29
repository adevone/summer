package summer.arch

import summer.DefaultSummerViewModel
import summer.SummerViewModel
import summer.ViewProxyProvider

actual abstract class ArchViewModel<TView> actual constructor() :
    DefaultSummerViewModel<TView>,
    ViewProxyProvider<TView>,
    SummerViewModel<TView>()