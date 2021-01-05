package summer.events

import kotlin.jvm.JvmField

class ViewEventExecution<TView>(
    @JvmField val performViewEvent: (TView, params: Array<out Any?>) -> Unit,
    @JvmField val params: Array<out Any?>,
    private val viewEventExecuted: (TView, ViewEventExecution<TView>) -> Unit
) {
    fun execute(view: TView) {
        performViewEvent(view, params)
        viewEventExecuted(view, this)
    }
}