package summer.events

class EventPerformer<TView, TEvent>(
    val performViewEvent: (TView, params: Array<out Any?>) -> Unit,
    val createInvokeProxyAdapter: (EventProxy<TView, *>) -> TEvent
)