package summer.events

import summer.ViewProvider

/**
 * Proxy that calls action following defined [SummerEventStrategy].
 * Can proxy actions with 0-12 arguments. Have child to each supported arity.
 */
sealed class SummerEvent<out TView, in TOwner : ViewProvider<TView>> {

    abstract val strategy: SummerEventStrategy<TView, TOwner>
    abstract fun viewCreated()

    class A0<out TView, in TOwner : ViewProvider<TView>>(
        private val getAction: (TView) -> (() -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : () -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke() {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action()
                }
            }
        }
    }

    class A1<out TView, in TOwner : ViewProvider<TView>, T1>(
        private val getAction: (TView) -> ((T1) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1)
                }
            }
        }
    }

    class A2<out TView, in TOwner : ViewProvider<TView>, T1, T2>(
        private val getAction: (TView) -> ((T1, T2) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2)
                }
            }
        }
    }

    class A3<out TView, in TOwner : ViewProvider<TView>, T1, T2, T3>(
        private val getAction: (TView) -> ((T1, T2, T3) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2, T3) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2, p3: T3) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3)
                }
            }
        }
    }

    class A4<out TView, in TOwner : ViewProvider<TView>, T1, T2, T3, T4>(
        private val getAction: (TView) -> ((T1, T2, T3, T4) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2, T3, T4) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2, p3: T3, p4: T4) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3, p4)
                }
            }
        }
    }

    class A5<out TView, in TOwner : ViewProvider<TView>, T1, T2, T3, T4, T5>(
        private val getAction: (TView) -> ((T1, T2, T3, T4, T5) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2, T3, T4, T5) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2, p3: T3, p4: T4, p5: T5) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3, p4, p5)
                }
            }
        }
    }

    class A6<out TView, in TOwner : ViewProvider<TView>, T1, T2, T3, T4, T5, T6>(
        private val getAction: (TView) -> ((T1, T2, T3, T4, T5, T6) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2, T3, T4, T5, T6) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2, p3: T3, p4: T4, p5: T5, p6: T6) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3, p4, p5, p6)
                }
            }
        }
    }

    class A7<out TView, in TOwner : ViewProvider<TView>, T1, T2, T3, T4, T5, T6, T7>(
        private val getAction: (TView) -> ((T1, T2, T3, T4, T5, T6, T7) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2, T3, T4, T5, T6, T7) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2, p3: T3, p4: T4, p5: T5, p6: T6, p7: T7) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3, p4, p5, p6, p7)
                }
            }
        }
    }

    class A8<out TView, in TOwner : ViewProvider<TView>, T1, T2, T3, T4, T5, T6, T7, T8>(
        private val getAction: (TView) -> ((T1, T2, T3, T4, T5, T6, T7, T8) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2, p3: T3, p4: T4, p5: T5, p6: T6, p7: T7, p8: T8) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3, p4, p5, p6, p7, p8)
                }
            }
        }
    }

    class A9<out TView, in TOwner : ViewProvider<TView>, T1, T2, T3, T4, T5, T6, T7, T8, T9>(
        private val getAction: (TView) -> ((T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2, p3: T3, p4: T4, p5: T5, p6: T6, p7: T7, p8: T8, p9: T9) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3, p4, p5, p6, p7, p8, p9)
                }
            }
        }
    }

    class A10<out TView, in TOwner : ViewProvider<TView>, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(
        private val getAction: (TView) -> ((T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2, p3: T3, p4: T4, p5: T5, p6: T6, p7: T7, p8: T8, p9: T9, p10: T10) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)
                }
            }
        }
    }

    class A11<out TView, in TOwner : ViewProvider<TView>, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(
        private val getAction: (TView) -> ((T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2, p3: T3, p4: T4, p5: T5, p6: T6, p7: T7, p8: T8, p9: T9, p10: T10, p11: T11) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)
                }
            }
        }
    }

    class A12<out TView, in TOwner : ViewProvider<TView>, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(
        private val getAction: (TView) -> ((T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit),
        private val owner: TOwner,
        override val strategy: SummerEventStrategy<TView, TOwner>
    ) : (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit, SummerEvent<TView, TOwner>() {

        override fun viewCreated() {
            strategy.viewCreated(owner)
        }
        
        override fun invoke(p1: T1, p2: T2, p3: T3, p4: T4, p5: T5, p6: T6, p7: T7, p8: T8, p9: T9, p10: T10, p11: T11, p12: T12) {
            strategy.called(owner) { view ->
                {
                    val action = getAction(view)
                    action(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)
                }
            }
        }
    }
}