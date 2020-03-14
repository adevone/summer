package summer.example.presentation.base

import summer.SummerPresenterWithRouter
import summer.example.AppKodeinAware

abstract class ScreenPresenter<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any> :
    SummerPresenterWithRouter<TViewState, TViewMethods, TRouter>(),
    AppKodeinAware {

    open fun onBackClick(): Boolean = false
}