val maxArity = 12

fun formatTypes(arity: Int) = (1..arity).joinToString { n -> "T$n" }
fun formatParams(arity: Int) = (1..arity).joinToString { n -> "ps[${n - 1}] as T${n}" }
fun formatInvokeParams(arity: Int) = (1..arity).joinToString { n -> "p$n" }

val text = """
package summer.events

import kotlin.jvm.JvmName

/**
 * Auto-generated code. See docs in [EventPerformer]
 */
@Suppress("UNCHECKED_CAST", "INAPPLICABLE_JVM_NAME")
interface EventPerformerFactory {
${
    (0..maxArity).joinToString(separator = "") { arity ->
        """ 
    val <TView, ${formatTypes(arity)}> GetViewEventHolder<TView, (${formatTypes(arity)}) -> Unit>.perform
        @JvmName("perform${arity}")
        get() = EventPerformer<TView, (${formatTypes(arity)}) -> Unit>(
            performViewEvent = { view, ps ->
                val event = getViewEvent(view)
                event(${formatParams(arity)})
            },
            createInvokeProxyAdapter = { proxy ->
                { ${formatInvokeParams(arity)} ->
                    proxy.invoke(${formatInvokeParams(arity)})
                }
            }
        )
        """
    }
}
}
""".trim()

println(text)