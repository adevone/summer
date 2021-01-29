package summer

import summer.events.strategies.ExactlyOnceStrategy
import summer.events.strategies.OnlyWhenAttachedStrategy
import summer.strategy.SerializationStore
import summer.strategy.SerializationStrategy

abstract class SerializationViewModel<TView> :
    RestoreViewModel<TView>(),
    OnlyWhenAttachedStrategy.ProxyFactory<TView>,
    ExactlyOnceStrategy.ProxyFactory<TView>,
    SerializationStrategy.Factory<TView>,
    SerializationStore.Holder {

    override var serializationStore = SerializationStore()
}