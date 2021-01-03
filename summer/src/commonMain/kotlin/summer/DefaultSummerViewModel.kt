package summer

import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy
import summer.events.strategies.DoExactlyOnceStrategy
import summer.events.strategies.DoOnlyWhenAttachedStrategy
import summer.state.StateProxyFactory
import summer.state.StateProxyStrategy
import summer.state.strategies.InMemoryStore
import summer.state.strategies.InMemoryStrategy

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
    StateProxyFactory<TView>,
    EventProxyFactory<TView, Any?>,
    InMemoryStrategy.ProxyFactory<TView>,
    InMemoryStore.Provider,
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
    RestoreViewModel<TView, Any?>() {

    /**
     * Used for state storing by default. Can be overridden.
     */
    override val inMemoryStore = InMemoryStore()

    override fun eventProxyOwner(): Any? = this
}