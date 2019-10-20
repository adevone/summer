package summer.example

import androidx.annotation.LayoutRes
import summer.SummerPresenterWithRouter
import summer.android.SummerFragmentWithRouter

abstract class ScreenFragment<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any,
        TPresenter : SummerPresenterWithRouter<TViewState, TViewMethods, TRouter>>(
    @LayoutRes contentLayoutId: Int
) : SummerFragmentWithRouter<TViewState, TViewMethods, TRouter, TPresenter>(
    contentLayoutId
), AppKodeinAware