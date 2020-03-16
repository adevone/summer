package summer.events

sealed class SummerEvent {

    class A0<TView>(
        private val getAction: (TView) -> (() -> Unit),
        private val strategy: SummerEventStrategy<TView>
    ) : () -> Unit, SummerEvent() {

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
        private val strategy: SummerEventStrategy<TView>
    ) : (T1) -> Unit, SummerEvent() {

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
        private val strategy: SummerEventStrategy<TView>
    ) : (T1, T2) -> Unit, SummerEvent() {

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
        private val strategy: SummerEventStrategy<TView>
    ) : (T1, T2, T3) -> Unit, SummerEvent() {

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