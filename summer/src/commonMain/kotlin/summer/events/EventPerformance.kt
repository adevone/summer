package summer.events

import kotlin.jvm.JvmField

/**
 * [EventProxy] creates in on each invocation.
 * Simplifies params application for [EventProxyStrategy]
 */
class EventPerformance<TView>(
    @JvmField val performViewEvent: (TView, params: Array<out Any?>) -> Unit,
    @JvmField val params: Array<out Any?>,
    private val viewEventExecuted: (TView, EventPerformance<TView>) -> Unit
) {
    fun performEvent(view: TView) {
        performViewEvent(view, params)
        viewEventExecuted(view, this)
    }
}