package summer

import summer.events.strategies.DoExactlyOnceStrategy
import summer.events.strategies.DoOnlyWhenAttachedStrategy
import summer.strategy.SerializationStore
import summer.strategy.SerializationStrategy

abstract class SerializationViewModel<TView> :
    RestoreViewModel<TView, Any?>(),
    DoOnlyWhenAttachedStrategy.ProxyFactory<TView>,
    DoExactlyOnceStrategy.ProxyFactory<TView>,
    SerializationStrategy.Factory<TView>,
    SerializationStore.Holder {

    override var serializationStore = SerializationStore()

    override fun eventProxyOwner(): GetViewProvider<TView> = this
}