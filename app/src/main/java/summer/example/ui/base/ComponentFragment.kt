package summer.example.ui.base

import summer.SummerPresenterWithRouter
import summer.android.SummerFragmentWithRouter
import summer.example.AppKodeinAware
import summer.example.ui.ArgsFragmentFeature
import summer.example.ui.base.routing.RouterProvider

abstract class ComponentFragment<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any,
        TPresenter : SummerPresenterWithRouter<TViewState, TViewMethods, TRouter>,
        TArgs> :
    SummerFragmentWithRouter<TViewState, TViewMethods, TRouter, TPresenter>(),
    RouterProvider,
    AppKodeinAware,
    ArgsFragmentFeature<TArgs> {

    override val ciceroneRouter get() = (parentFragment as RouterProvider).ciceroneRouter
}