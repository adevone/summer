package summer.events

/**
 * Transforms lambda calls with multiple params
 * into calls with all params stored in Array and vice versa.
 *
 * [EventPerformerFactory] contains [EventPerformerFactory.perform]
 * factory property getters for arity 0-12
 */
class EventPerformer<TView, TEvent>(
    val performViewEvent: (TView, params: Array<out Any?>) -> Unit,
    val createInvokeProxyAdapter: (EventProxy<TView, *>) -> TEvent
)