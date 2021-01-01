package summer

import summer.events.DoExactlyOnceStrategy
import summer.events.DoOnlyWhenAttachedStrategy
import summer.strategy.SerializationStateProvider
import summer.strategy.SerializationStore
import summer.strategy.SerializationStrategy

abstract class SerializationViewModel<TView> :
    RestoreViewModel<TView, SerializationStateProvider, Any?>(),
    DoOnlyWhenAttachedStrategy.Factory<TView>,
    DoExactlyOnceStrategy.Factory<TView>,
    SerializationStrategy.Factory<TView>,
    SerializationStateProvider {

    override var serializationStore = SerializationStore()

    override fun eventsOwner(): GetViewProvider<TView> = this
    override fun stateOwner(): SerializationStateProvider = this
}