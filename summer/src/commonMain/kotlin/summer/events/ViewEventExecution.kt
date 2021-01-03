package summer.events

import kotlin.jvm.JvmField

class ViewEventExecution<TView, in TOwner>(
    @JvmField val getViewEvent: (TView) -> Function<Unit>,
    @JvmField val params: Array<out Any?>,
    private val listener: EventProxyListener<TView, TOwner>?,
    private val owner: TOwner,
    private val strategy: EventProxyStrategy<TView, TOwner>,
) {
    fun execute(view: TView) {
        val event = getViewEvent(view)
        try {
            when (params.size) {
                0 -> castEvent<() -> Unit>(event).invoke()
                1 -> castEvent<(Any?) -> Unit>(event).invoke(
                    params[0]
                )
                2 -> castEvent<(Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1]
                )
                3 -> castEvent<(Any?, Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1], params[2]
                )
                4 -> castEvent<(Any?, Any?, Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1], params[2], params[3]
                )
                5 -> castEvent<(Any?, Any?, Any?, Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1], params[2], params[3], params[4]
                )
                6 -> castEvent<(Any?, Any?, Any?, Any?, Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1], params[2], params[3], params[4], params[5]
                )
                7 -> castEvent<(Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1], params[2], params[3], params[4], params[5], params[6]
                )
                8 -> castEvent<(Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]
                )
                9 -> castEvent<(Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]
                )
                10 -> castEvent<(Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9]
                )
                11 -> castEvent<(Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10]
                )
                12 -> castEvent<(Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit>(event).invoke(
                    params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11]
                )
                else -> {
                    // There is also compile-time check on this.
                    // Must never happens if all strategy factories implemented correctly
                    throw IllegalArgumentException("Event arity can not be higher than 12")
                }
            }
        } catch (e: ClassCastException) {
            throw IllegalArgumentException(
                "Possible signature mismatch. Check does you correctly proxy events in viewProxy",
                e
            )
        }
        listener?.viewEventExecuted(view, strategy, execution = this, owner)
    }

    private inline fun <reified TEvent> castEvent(event: Function<Unit>): TEvent {
        return event as? TEvent ?: throw IllegalArgumentException(
            "Signature mismatch. Check does you correctly proxies events in viewProxy"
        )
    }
}