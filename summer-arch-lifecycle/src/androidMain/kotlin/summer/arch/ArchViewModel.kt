package summer.arch

import androidx.lifecycle.ViewModel
import summer.DefaultSummerViewModel
import summer.ViewProxyProvider

actual abstract class ArchViewModel<TView> private constructor(
    impl: BindViewModelImpl<TView>
) : ViewModel(),
    ViewModelBinder<TView> by impl,
    DefaultSummerViewModel<TView> by impl,
    ViewProxyProvider<TView> {

    actual constructor() : this(BindViewModelImpl())
}