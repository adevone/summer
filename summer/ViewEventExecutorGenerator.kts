fun finishSignature(arity: Int): String {
    return (1..arity).joinToString(separator = "") { n -> ", T${n}" }
}

fun lambdaParams(arity: Int): String {
    return (1..arity).joinToString { n -> "T$n" }
}

fun namedParams(arity: Int): String {
    return (1..arity).joinToString { n -> "p$n: T$n" }
}

fun passParams(arity: Int): String {
    return (1..arity).joinToString { n -> "p$n" }
}

fun executorConstructor(arity: Int): String {
    return if (arity != 0)
        "(${(1..arity).joinToString { n -> "@JvmField val p$n: T$n" }})"
    else
        ""
}

for (arity in 0..12) {
    println("""
class A${arity}<TView, in TOwner${finishSignature(arity)}>(
    private val getViewEvent: (TView) -> ((${lambdaParams(arity)}) -> Unit),
    private val owner: TOwner,
    private val getViewProvider: GetViewProvider<TView>,
    private val listener: EventProxyListener<TView, TOwner>?,
    override val strategy: EventProxyStrategy<TView, TOwner>
) : (${lambdaParams(arity)}) -> Unit, EventProxy<TView, TOwner>() {

    override fun viewCreated() {
        strategy.viewCreated(owner, getViewProvider)
    }

    override fun invoke(${namedParams(arity)}) {
        val executor = EventExecutor(${passParams(arity)})
        strategy.proxyInvoked(executor, owner, getViewProvider)
        listener?.proxyInvoked(strategy, executor, owner)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    inner class EventExecutor${executorConstructor(arity)} : ViewEventExecutor<TView> {

        override fun execute(view: TView) {
            val viewEvent = getViewEvent(view)
            viewEvent(${passParams(arity)})
            listener?.viewEventExecuted(view, strategy, viewEventExecutor = this, owner)
        }
    }
}""")
}