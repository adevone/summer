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

fun executorProperties(arity: Int): String {
    return (1..arity).joinToString { n -> "@JvmField val p$n: T$n" }
}

for (arity in 0..12) {
    println("""
class A${arity}<TView, in TOwner${finishSignature(arity)}>(
    private val getAction: (TView) -> ((${lambdaParams(arity)}) -> Unit),
    private val owner: TOwner,
    private val getViewProvider: GetViewProvider<TView>,
    private val listener: EventListener<TView, TOwner>?,
    override val strategy: SummerEventStrategy<TView, TOwner>
) : (${lambdaParams(arity)}) -> Unit, SummerEvent<TView, TOwner>() {

    override fun viewCreated() {
        strategy.viewCreated(owner, getViewProvider)
    }

    override fun invoke(${namedParams(arity)}) {
        val executor = EventExecutor(${passParams(arity)})
        strategy.called(executor, owner, getViewProvider)
        listener?.called(strategy, executor, owner)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    inner class EventExecutor(${executorProperties(arity)}) : ViewEventExecutor<TView> {

        override fun execute(view: TView) {
            val action = getAction(view)
            action(${passParams(arity)})
            listener?.executedOnView(view, strategy, viewEventExecutor = this, owner)
        }
    }
}""")
}