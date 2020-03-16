package summer.events

sealed class SummerEvent<TView> {

    abstract val strategy: SummerEventStrategy<TView>

    fun viewCreated(view: TView) {
        strategy.viewCreated(view)
    }

    fun viewDestroyed() {
        strategy.viewDestroyed()
    }

    class A0<TView>(
        private val getAction: (TView) -> (() -> Unit),
        override val strategy: SummerEventStrategy<TView>
    ) : () -> Unit, SummerEvent<TView>() {

        override fun invoke() {
            strategy.called { view ->
                {
                    val action = getAction(view)
                    action()
                }
            }
        }
    }

    class A1<TView, T1>(
        private val getAction: (TView) -> ((T1) -> Unit),
        override val strategy: SummerEventStrategy<TView>
    ) : (T1) -> Unit, SummerEvent<TView>() {

        override fun invoke(p1: T1) {
            strategy.called { view ->
                {
                    val action = getAction(view)
                    action(p1)
                }
            }
        }
    }

    class A2<TView, T1, T2>(
        private val getAction: (TView) -> ((T1, T2) -> Unit),
        override val strategy: SummerEventStrategy<TView>
    ) : (T1, T2) -> Unit, SummerEvent<TView>() {

        override fun invoke(p1: T1, p2: T2) {
            strategy.called { view ->
                {
                    val action = getAction(view)
                    action(p1, p2)
                }
            }
        }
    }

    class A3<TView, T1, T2, T3>(
        private val getAction: (TView) -> ((T1, T2, T3) -> Unit),
        override val strategy: SummerEventStrategy<TView>
    ) : (T1, T2, T3) -> Unit, SummerEvent<TView>() {

        override fun invoke(p1: T1, p2: T2, p3: T3) {
            strategy.called { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3)
                }
            }
        }
    }
}