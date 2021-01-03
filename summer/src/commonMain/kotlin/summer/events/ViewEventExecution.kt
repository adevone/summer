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
        @Suppress("UNCHECKED_CAST")
        when (params.size) {
            0 -> (event as () -> Unit)()
            1 -> (event as (Any?) -> Unit)(params[0])
            2 -> (event as (Any?, Any?) -> Unit)(params[0], params[1])
            3 -> (event as (Any?, Any?, Any?) -> Unit)(params[0], params[1], params[2])
            4 -> (event as (Any?, Any?, Any?, Any?) -> Unit)(params[0], params[1], params[2], params[3])
            5 -> (event as (Any?, Any?, Any?, Any?, Any?) -> Unit)(params[0], params[1], params[2], params[3], params[4])
            6 -> (event as (Any?, Any?, Any?, Any?, Any?, Any?) -> Unit)(params[0], params[1], params[2], params[3], params[4], params[5])
            7 -> (event as (Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit)(params[0], params[1], params[2], params[3], params[4], params[5], params[6])
            8 -> (event as (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit)(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7])
            9 -> (event as (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit)(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8])
            10 -> (event as (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit)(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9])
            11 -> (event as (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit)(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10])
            12 -> (event as (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Unit)(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11])
        }
        listener?.viewEventExecuted(view, strategy, execution = this, owner)
    }
}