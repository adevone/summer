package summer

import summer.events.strategies.DoExactlyOnceStrategy
import summer.events.strategies.DoOnlyWhenAttachedStrategy
import summer.strategy.SerializationStateProvider
import summer.strategy.SerializationStore
import summer.strategy.SerializationStrategy

abstract class SerializationViewModel<TView> :
    RestoreViewModel<TView, SerializationStateProvider, Any?>(),
    DoOnlyWhenAttachedStrategy.ProxyFactory<TView>,
    DoExactlyOnceStrategy.ProxyFactory<TView>,
    SerializationStrategy.Factory<TView>,
    SerializationStateProvider {

    override var serializationStore = SerializationStore()

    override fun eventProxyOwner(): GetViewProvider<TView> = this
    override fun stateProxyOwner(): SerializationStateProvider = this
}