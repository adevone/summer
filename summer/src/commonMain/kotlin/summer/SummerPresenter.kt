package summer

import summer.execution.SummerExecutor
import summer.view.SummerViewController
import summer.view.SummerViewStateProxyProvider
import summer.store.InMemoryStore
import summer.store.SummerStore
import summer.store.SummerStoresSubscriber
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty0

/**
 * Base presenter. Allows to restore view state and
 * execute summer sources ([summer.execution.source.SummerSource],
 * [summer.execution.reducer.SummerReducer] or [summer.execution.mix.MixSource])
 *
 * Should not be used as direct parent of feature presenters.
 * You should create your own base presenter in your project.
 */
abstract class SummerPresenter<TViewState, TViewMethods>(
    uiContext: CoroutineContext = defaultUiContext,
    defaultWorkContext: CoroutineContext = defaultBackgroundContext,
    loggerFactory: SummerLogger.Factory = DefaultLoggerFactory,
    /**
     * Store created specifically for this presenter. Must not be reused
     */
    private val localStore: SummerStore = InMemoryStore()
) : SummerExecutor(
    mainContext = uiContext,
    defaultWorkContext = defaultWorkContext,
    loggerFactory = loggerFactory
), SummerViewStateProxyProvider<TViewState> {

    private val storesController = SummerStoresSubscriber()
    private val stateHolder = SummerViewController<TViewState, TViewMethods>(storesController)

    protected val viewStateProxy: TViewState
        get() = stateHolder.viewStateProxy ?: throw ViewStateDoesNotExistException()

    protected val viewMethods: TViewMethods?
        get() = stateHolder.viewMethods

    /**
     * Must be called when view is created. May be called multiple times
     */
    fun viewCreated(
        viewState: TViewState,
        viewMethods: TViewMethods
    ) {
        stateHolder.viewCreated(
            viewState,
            viewMethods,
            viewStateProxyProvider = this
        )
    }

    /**
     * Must be called when view is destroyed. May be called multiple times
     */
    fun viewDestroyed() {
        stateHolder.viewDestroyed()
    }

    /**
     * Shorthand for [storeIn] with [localStore] of this presenter
     */
    protected fun <T> store(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T
    ) = storeIn(
        viewStateProperty = viewStateProperty,
        initialValue = initialValue,
        store = localStore
    )

    /**
     * Create delegate for property stored in any store
     *
     * May be called in createViewStateProxy method or just
     * in presenter if some sort of persistent store is used.
     *
     * If viewStateProperty is not null value will be stored in store
     * and mirrored in viewStateProperty if view is not destroyed
     *
     * If viewStateProperty is null value will be stored only in store
     *
     * @return stored property delegate
     */
    protected fun <T> storeIn(
        viewStateProperty: KMutableProperty0<T>? = null,
        initialValue: T,
        store: SummerStore
    ) = storesController.storeIn(
        mirrorProperty = viewStateProperty,
        initialValue = initialValue,
        store = store
    )

    /**
     * Must be called when presenter is created. Must be called exactly once
     */
    fun created() {
        super.receiverCreated()
    }

    /**
     * Must be called when presenter is destroyed. Must be called exactly once
     */
    fun destroyed() {
        super.receiverDestroyed()
    }

    /**
     * Must be called when user sees view for the first time
     */
    fun entered() {
        onEnter()
    }

    /**
     * Must be called when view popped from stack
     */
    fun exited() {
        onExit()
    }

    /**
     * Must be called every time when view appears (see [onAppear])
     */
    fun appeared() {
        onAppear()
    }

    /**
     * Must be called every time when view disappears (see [onDisappear])
     */
    fun disappeared() {
        onDisappear()
    }

    /**
     * Called when user sees view for the first time
     */
    protected open fun onEnter() {}

    /**
     * Called when view popped from stack
     */
    protected open fun onExit() {}

    /**
     * Called every time view appears.
     * It may happen when user opens view for the first time or switches to your app
     */
    protected open fun onAppear() {}

    /**
     * Called every time when view disappears.
     * It may happen when user pops view from stack or switches to another app
     */
    protected open fun onDisappear() {}

    /**
     * Convenient constructor if IoC container used
     */
    constructor(dependencies: Dependencies) : this(
        uiContext = dependencies.uiContext,
        defaultWorkContext = dependencies.workContext,
        loggerFactory = dependencies.loggerFactory,
        localStore = dependencies.localStore
    )

    class Dependencies(
        val uiContext: CoroutineContext = defaultUiContext,
        val workContext: CoroutineContext = defaultBackgroundContext,
        val loggerFactory: SummerLogger.Factory = DefaultLoggerFactory,
        val localStore: SummerStore = InMemoryStore()
    )
}

class ViewStateDoesNotExistException : IllegalStateException(
    "can not use viewStateProxy when view does not exist"
)