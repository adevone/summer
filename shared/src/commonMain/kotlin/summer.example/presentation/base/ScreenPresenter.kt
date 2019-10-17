package summer.example.presentation.base

import org.kodein.di.KodeinProperty
import org.kodein.di.direct
import org.kodein.di.erased.instance
import summer.SummerPresenter
import summer.example.di

abstract class ScreenPresenter<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any>
    : SummerPresenter<TViewState, TViewMethods, TRouter>() {

    open fun onBackClick(): Boolean = false

    inline fun <reified T : Any> get(): T = di.direct.instance()

    inline fun <reified T : Any> instance(): KodeinProperty<T> = di.instance()
}