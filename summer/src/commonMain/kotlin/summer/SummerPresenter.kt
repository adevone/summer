package summer

import summer.execution.SummerExecutor
import summer.store.SummerStoresController
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty0

abstract class SummerPresenter<
        TViewState,
        TViewMethods,
        TRouter>(
    private val localStore: SummerStore,
    workContext: CoroutineContext,
    uiContext: CoroutineContext,
    loggerFactory: SummerLogger.Factory
) : SummerExecutor(
    mainContext = uiContext,
    workContext = workContext,
    loggerFactory = loggerFactory
) {
    private val storesController = SummerStoresController()

    protected abstract fun createViewStateProxy(vs: TViewState): TViewState

    private var _viewStateProxy: TViewState? = null
    protected val viewStateProxy: TViewState get() = _viewStateProxy!!

    protected var viewMethods: TViewMethods? = null

    private var _router: TRouter? = null
    protected val router: TRouter get() = _router!!

    fun created() {
        receiverCreated()
    }

    fun destroyed() {
        receiverDestroyed()
    }

    fun viewCreated(
        viewState: TViewState,
        viewMethods: TViewMethods,
        router: TRouter
    ) {
        this._viewStateProxy = createViewStateProxy(viewState)
        this.viewMethods = viewMethods
        this._router = router

        // onConnect call placed there because presenter methods may be called in initView.
        // Presenter must be initialized at that moment
        storesController.onMirrorConnect()
    }

    fun viewDestroyed() {
        storesController.onMirrorDisconnect()
        this.viewMethods = null
        this._router = null
    }

    protected fun <T> store(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T
    ) = storeIn(
        viewStateProperty = viewStateProperty,
        initialValue = initialValue,
        store = localStore
    )

    protected fun <T> storeIn(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T,
        store: SummerStore
    ) = storesController.storeIn(
        mirrorProperty = viewStateProperty,
        initialValue = initialValue,
        store = store
    )

    fun entered() {
        onEnter()
    }

    fun appeared() {
        onAppear()
    }

    fun disappeared() {
        onDisappear()
    }

    fun exited() {
        onExit()
    }

    protected open fun onEnter() {}

    protected open fun onExit() {}

    protected open fun onAppear() {}

    protected open fun onDisappear() {}
}