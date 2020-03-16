package summer

import summer.store.InMemoryStore
import summer.store.SummerStore
import kotlin.coroutines.CoroutineContext

abstract class SummerPresenterWithRouter<TView, TRouter>(
    uiContext: CoroutineContext = defaultUiContext,
    defaultWorkContext: CoroutineContext = defaultBackgroundContext,
    loggerFactory: SummerLogger.Factory = DefaultLoggerFactory,
    /**
     * Store created specifically for this presenter. Must not be reused
     */
    localStore: SummerStore = InMemoryStore()
) : SummerPresenter<TView>(
    uiContext,
    defaultWorkContext,
    loggerFactory,
    localStore
), UnsafePresenterLifecycleOwnerWithRouter {

    protected val router: TRouter
        get() = _router ?: throw RouterDoesNotExistException()

    private var _router: TRouter? = null

    /**
     * Must be called when router is destroyed. May be called multiple times
     */
    fun routerCreated(router: TRouter) {
        this._router = router
    }

    override fun routerCreatedUnsafe(router: Any) {
        @Suppress("UNCHECKED_CAST")
        routerCreated(router as TRouter)
    }

    override fun routerDestroyed() {
        this._router = null
    }
}

class RouterDoesNotExistException : IllegalStateException(
    "can not use router when it does not exist"
)

/**
 * Same as [UnsafePresenterLifecycleOwner] but with router events
 */
interface UnsafePresenterLifecycleOwnerWithRouter : UnsafePresenterLifecycleOwner {

    /**
     * Same as [SummerPresenterWithRouter.routerCreated] but with unsafe typecast.
     * Used when view can not pass typed [router]
     */
    fun routerCreatedUnsafe(router: Any)

    /**
     * Must be called before router will destroyed. May be called multiple times
     */
    fun routerDestroyed()
}