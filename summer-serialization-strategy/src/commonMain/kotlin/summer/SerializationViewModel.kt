package summer

import summer.events.DoExactlyOnceStrategy
import summer.events.DoOnlyWhenAttachedStrategy
import summer.strategy.SerializationStateProvider
import summer.strategy.SerializationStore
import summer.strategy.SerializationStrategy

abstract class SerializationViewModel<TView> :
    RestoreSummerViewModel<TView, SerializationStateProvider, ViewProvider<TView>>(),
    DoOnlyWhenAttachedStrategy.Factory<TView>,
    DoExactlyOnceStrategy.Factory<TView>,
    SerializationStrategy.Factory<TView>,
    SerializationStateProvider {

    override var serializationStore = SerializationStore()

    override fun getEventsOwner(): ViewProvider<TView> = this
    override fun getStateOwner(): SerializationStateProvider = this
}