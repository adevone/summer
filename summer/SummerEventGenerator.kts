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
    return (1..arity).joinToString(separator = "") { n -> "p$n, " }
}

fun executorProperties(arity: Int): String {
    return if (arity != 0) "\n" + (1..arity).joinToString { n -> "@JvmField val p$n: T$n" } + "," else ""
}

for (arity in 0..12) {
    println("""
class A${arity}<TView, in TOwner : GetViewProvider<TView>${finishSignature(arity)}>(
    private val getAction: (TView) -> ((${lambdaParams(arity)}) -> Unit),
    private val owner: TOwner,
    override val strategy: SummerEventStrategy<TView, TOwner>
) : (${lambdaParams(arity)}) -> Unit, SummerEvent<TView, TOwner>() {

    override fun viewCreated() {
        strategy.viewCreated(owner)
    }

    override fun invoke(${namedParams(arity)}) {
        val executor = EventExecutor(${passParams(arity)}getAction)
        strategy.called(owner, executor)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    class EventExecutor<TView${finishSignature(arity)}>(${executorProperties(arity)}
        private val getAction: (TView) -> ((${lambdaParams(arity)}) -> Unit)
    ) : ViewEventExecutor<TView> {

        override fun execute(view: TView) {
            val action = getAction(view)
            action(${passParams(arity)})
        }
    }
}""")
}