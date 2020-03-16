package summer.example.ui.base

import summer.SummerPresenter
import summer.android.SummerFragment
import summer.example.AppKodeinAware
import summer.example.ui.ArgsFragmentFeature
import summer.example.ui.base.routing.RouterProvider

abstract class ComponentFragment<
        TView : Any,
        TPresenter : SummerPresenter<TView>,
        TArgs> :
    SummerFragment<TView, TPresenter>(),
    RouterProvider,
    AppKodeinAware,
    ArgsFragmentFeature<TArgs> {

    override val ciceroneRouter get() = (parentFragment as RouterProvider).ciceroneRouter
}