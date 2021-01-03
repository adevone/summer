package summer

import summer.events.*
import summer.state.*

/**
 * View model with support of default
 * - [EventProxyStrategy]: [DoOnlyWhenAttachedStrategy], [DoExactlyOnceStrategy]
 * - [StateProxyStrategy]: [InMemoryStrategy]
 *
 * Implemented in [DefaultSummerViewModelImpl]
 *
 * @param [TView] see [LifecycleViewModel]
 */
interface DefaultSummerViewModel<TView> :
    LifecycleViewModel<TView>,
    StateProxyFactory<TView, InMemoryStoreProvider>,
    EventProxyFactory<TView, Any?>,
    InMemoryStrategy.ProxyFactory<TView>,
    InMemoryStoreProvider,
    DoOnlyWhenAttachedStrategy.ProxyFactory<TView>,
    DoExactlyOnceStrategy.ProxyFactory<TView>

/**
 * Implementation of [DefaultSummerViewModel].
 * Can be used as a base class or an interface delegate.
 *
 * Example of a delegation:
 * class MyViewModel<TView> :
 *   <parent class that prohibits inheritance from [DefaultSummerViewModelImpl]>,
 *   DefaultSummerViewModel<TView> by DefaultSummerViewModelImpl()
 */
open class DefaultSummerViewModelImpl<TView> :
    DefaultSummerViewModel<TView>,
    RestoreViewModel<TView, InMemoryStoreProvider, Any?>() {

    /**
     * Used for state storing by default. Can be overridden.
     */
    override val inMemoryStore = InMemoryStore()

    override fun eventProxyOwner(): Any? = this
    override fun stateProxyOwner(): InMemoryStoreProvider = this
}