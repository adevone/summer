package summer

import summer.execution.SummerExecutor
import summer.store.InMemoryStore
import summer.store.SummerStore
import kotlin.coroutines.CoroutineContext

abstract class SummerPresenterWithRouter<TViewState, TViewMethods, TRouter>(
    uiContext: CoroutineContext = defaultUiContext,
    defaultWorkContext: CoroutineContext = defaultBackgroundContext,
    loggerFactory: SummerLogger.Factory = DefaultLoggerFactory,
    /**
     * Store created specifically for this presenter. Must not be reused
     */
    localStore: SummerStore = InMemoryStore()
) : SummerPresenter<TViewState, TViewMethods>(
    SummerExecutor(
        uiContext = uiContext,
        defaultWorkContext = defaultWorkContext,
        loggerFactory = loggerFactory
    ),
    localStore
) {
    protected val router: TRouter
        get() = _router ?: throw RouterDoesNotExistException()

    private var _router: TRouter? = null

    /**
     * Must be called when router is destroyed. May be called multiple times
     */
    fun routerCreated(router: TRouter) {
        this._router = router
    }

    /**
     * Must be called before router will destroyed. May be called multiple times
     */
    fun routerDestroyed() {
        this._router = null
    }
}

class RouterDoesNotExistException : IllegalStateException(
    "can not use router when it does not exist"
)