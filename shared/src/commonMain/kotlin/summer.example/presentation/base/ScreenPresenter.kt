package ru.napoleonit.example.presentation.base

import org.kodein.di.KodeinProperty
import org.kodein.di.direct
import org.kodein.di.erased.instance
import summer.example.di
import summer.SummerPresenter
import summer.summer.ExceptionsHandler
import summer.summer.StateHolder
import kotlin.coroutines.CoroutineContext

abstract class ScreenPresenter<
        TViewState,
        TViewMethods,
        TRouter>
    : BasePresenter<TViewState, TViewMethods, TRouter>(
    di.direct.instance()
) {
    open fun onBackClick(): Boolean = false

    inline fun <reified T : Any> get(): T = di.direct.instance()

    inline fun <reified T : Any> instance(): KodeinProperty<T> = di.instance()
}

abstract class BasePresenter<
        TViewState,
        TViewMethods,
        TRouter>(
    dependencies: Dependencies
) : SummerPresenter<TViewState, TViewMethods, TRouter>(
    exceptionsHandler = dependencies.exceptionsHandler,
    stateHolder = dependencies.stateHolder,
    workContext = dependencies.workContext,
    uiContext = dependencies.uiContext
) {
    class Dependencies(
        val exceptionsHandler: ExceptionsHandler,
        val stateHolder: StateHolder,
        val workContext: CoroutineContext,
        val uiContext: CoroutineContext
    )
}