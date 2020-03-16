package summer.example.presentation.base

import summer.SummerPresenter
import summer.example.AppKodeinAware

abstract class ScreenPresenter<TView : Any> : SummerPresenter<TView>(), AppKodeinAware {
    open fun onBackClick(): Boolean = false
}