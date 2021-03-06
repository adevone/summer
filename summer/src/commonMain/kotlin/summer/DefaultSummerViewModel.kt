package summer

import summer.events.EventProxyFactory
import summer.events.EventProxyStrategy
import summer.events.strategies.ExactlyOnceStrategy
import summer.events.strategies.OnlyWhenAttachedStrategy
import summer.state.StateProxyFactory
import summer.state.StateProxyStrategy
import summer.state.strategies.InMemoryStrategy

/**
 * View model with support of default
 * - [EventProxyStrategy]: [OnlyWhenAttachedStrategy], [ExactlyOnceStrategy]
 * - [StateProxyStrategy]: [InMemoryStrategy]
 *
 * Implemented in [DefaultSummerViewModelImpl]
 *
 * @param [TView] see [LifecycleViewModel]
 */
interface DefaultSummerViewModel<TView> :
    LifecycleViewModel<TView>,
    StateProxyFactory<TView>,
    EventProxyFactory<TView>,
    InMemoryStrategy.ProxyFactory<TView>,
    OnlyWhenAttachedStrategy.ProxyFactory<TView>,
    ExactlyOnceStrategy.ProxyFactory<TView>

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
    RestoreViewModel<TView>()