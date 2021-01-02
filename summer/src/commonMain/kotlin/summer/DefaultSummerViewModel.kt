package summer

import summer.events.*
import summer.state.*

/**
 * View model with support of default
 * - [SummerEventStrategy]: [DoOnlyWhenAttachedStrategy], [DoExactlyOnceStrategy]
 * - [SummerStateStrategy]: [InMemoryStateStrategy]
 *
 * Implemented in [DefaultSummerViewModelImpl]
 *
 * @param [TView] see [LifecycleViewModel]
 */
interface DefaultSummerViewModel<TView> :
    LifecycleViewModel<TView>,
    StateFactory<TView, InMemoryStoreProvider>,
    EventFactory<TView, Any?>,
    InMemoryStateStrategy.Factory<TView>,
    InMemoryStoreProvider,
    DoOnlyWhenAttachedStrategy.Factory<TView>,
    DoExactlyOnceStrategy.Factory<TView>

/**
 * Implementation of [DefaultSummerViewModel].
 * Can be used as a base class or an interface delegate.
 *
 * Example of a delegation:
 * class MyViewModel<TView> :
 *     <parent class that prohibits inheritance from DefaultSummerViewModelImpl>,
 *     DefaultSummerViewModel<TView> by DefaultSummerViewModelImpl()
 */
open class DefaultSummerViewModelImpl<TView> :
    DefaultSummerViewModel<TView>,
    RestoreViewModel<TView, InMemoryStoreProvider, Any?>() {

    /**
     * Used for state storing by default. Can be overridden.
     */
    override val inMemoryStore = InMemoryStore()

    override fun eventsOwner(): Any? = this
    override fun stateOwner(): InMemoryStoreProvider = this
}