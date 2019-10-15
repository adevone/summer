package summer.example.presentation.base

import org.kodein.di.KodeinProperty
import org.kodein.di.direct
import org.kodein.di.erased.instance
import summer.SummerLogger
import summer.SummerPresenter
import summer.example.di
import summer.store.InMemoryStore
import kotlin.coroutines.CoroutineContext

abstract class ScreenPresenter<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any>
    : BasePresenter<TViewState, TViewMethods, TRouter>(
    di.direct.instance()
) {
    open fun onBackClick(): Boolean = false

    inline fun <reified T : Any> get(): T = di.direct.instance()

    inline fun <reified T : Any> instance(): KodeinProperty<T> = di.instance()
}

abstract class BasePresenter<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any>(
    dependencies: Dependencies
) : SummerPresenter<TViewState, TViewMethods, TRouter>(
    localStore = InMemoryStore(),
    workContext = dependencies.workContext,
    uiContext = dependencies.uiContext,
    loggerFactory = dependencies.loggerFactory
) {
    class Dependencies(
        val workContext: CoroutineContext,
        val uiContext: CoroutineContext,
        val loggerFactory: SummerLogger.Factory
    )
}