package summer.events

import kotlin.jvm.JvmName

/**
 * Auto-generated code. See docs in [EventPerformer]
 */
@Suppress("UNCHECKED_CAST", "INAPPLICABLE_JVM_NAME")
interface EventPerformerFactory {

    val <TView> GetViewEventHolder<TView, () -> Unit>.perform
        @JvmName("perform0")
        get() = EventPerformer<TView, () -> Unit>(
            performViewEvent = { view, _ ->
                val event = this.getViewEvent(view)
                event()
            },
            createInvokeProxyAdapter = { proxy ->
                {
                    proxy.invoke()
                }
            }
        )

    val <TView, T1> GetViewEventHolder<TView, (T1) -> Unit>.perform
        @JvmName("perform1")
        get() = EventPerformer<TView, (T1) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1 ->
                    proxy.invoke(p1)
                }
            }
        )

    val <TView, T1, T2> GetViewEventHolder<TView, (T1, T2) -> Unit>.perform
        @JvmName("perform2")
        get() = EventPerformer<TView, (T1, T2) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2 ->
                    proxy.invoke(p1, p2)
                }
            }
        )

    val <TView, T1, T2, T3> GetViewEventHolder<TView, (T1, T2, T3) -> Unit>.perform
        @JvmName("perform3")
        get() = EventPerformer<TView, (T1, T2, T3) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2, ps[2] as T3)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2, p3 ->
                    proxy.invoke(p1, p2, p3)
                }
            }
        )

    val <TView, T1, T2, T3, T4> GetViewEventHolder<TView, (T1, T2, T3, T4) -> Unit>.perform
        @JvmName("perform4")
        get() = EventPerformer<TView, (T1, T2, T3, T4) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2, ps[2] as T3, ps[3] as T4)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2, p3, p4 ->
                    proxy.invoke(p1, p2, p3, p4)
                }
            }
        )

    val <TView, T1, T2, T3, T4, T5> GetViewEventHolder<TView, (T1, T2, T3, T4, T5) -> Unit>.perform
        @JvmName("perform5")
        get() = EventPerformer<TView, (T1, T2, T3, T4, T5) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2, ps[2] as T3, ps[3] as T4, ps[4] as T5)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2, p3, p4, p5 ->
                    proxy.invoke(p1, p2, p3, p4, p5)
                }
            }
        )

    val <TView, T1, T2, T3, T4, T5, T6> GetViewEventHolder<TView, (T1, T2, T3, T4, T5, T6) -> Unit>.perform
        @JvmName("perform6")
        get() = EventPerformer<TView, (T1, T2, T3, T4, T5, T6) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2, ps[2] as T3, ps[3] as T4, ps[4] as T5, ps[5] as T6)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2, p3, p4, p5, p6 ->
                    proxy.invoke(p1, p2, p3, p4, p5, p6)
                }
            }
        )

    val <TView, T1, T2, T3, T4, T5, T6, T7> GetViewEventHolder<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>.perform
        @JvmName("perform7")
        get() = EventPerformer<TView, (T1, T2, T3, T4, T5, T6, T7) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2, ps[2] as T3, ps[3] as T4, ps[4] as T5, ps[5] as T6, ps[6] as T7)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2, p3, p4, p5, p6, p7 ->
                    proxy.invoke(p1, p2, p3, p4, p5, p6, p7)
                }
            }
        )

    val <TView, T1, T2, T3, T4, T5, T6, T7, T8> GetViewEventHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>.perform
        @JvmName("perform8")
        get() = EventPerformer<TView, (T1, T2, T3, T4, T5, T6, T7, T8) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2, ps[2] as T3, ps[3] as T4, ps[4] as T5, ps[5] as T6, ps[6] as T7, ps[7] as T8)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2, p3, p4, p5, p6, p7, p8 ->
                    proxy.invoke(p1, p2, p3, p4, p5, p6, p7, p8)
                }
            }
        )

    val <TView, T1, T2, T3, T4, T5, T6, T7, T8, T9> GetViewEventHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>.perform
        @JvmName("perform9")
        get() = EventPerformer<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2, ps[2] as T3, ps[3] as T4, ps[4] as T5, ps[5] as T6, ps[6] as T7, ps[7] as T8, ps[8] as T9)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2, p3, p4, p5, p6, p7, p8, p9 ->
                    proxy.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9)
                }
            }
        )

    val <TView, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> GetViewEventHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>.perform
        @JvmName("perform10")
        get() = EventPerformer<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2, ps[2] as T3, ps[3] as T4, ps[4] as T5, ps[5] as T6, ps[6] as T7, ps[7] as T8, ps[8] as T9, ps[9] as T10)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10 ->
                    proxy.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)
                }
            }
        )

    val <TView, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> GetViewEventHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>.perform
        @JvmName("perform11")
        get() = EventPerformer<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2, ps[2] as T3, ps[3] as T4, ps[4] as T5, ps[5] as T6, ps[6] as T7, ps[7] as T8, ps[8] as T9, ps[9] as T10, ps[10] as T11)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11 ->
                    proxy.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)
                }
            }
        )

    val <TView, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> GetViewEventHolder<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>.perform
        @JvmName("perform12")
        get() = EventPerformer<TView, (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> Unit>(
            performViewEvent = { view, ps ->
                val event = this.getViewEvent(view)
                event(ps[0] as T1, ps[1] as T2, ps[2] as T3, ps[3] as T4, ps[4] as T5, ps[5] as T6, ps[6] as T7, ps[7] as T8, ps[8] as T9, ps[9] as T10, ps[10] as T11, ps[11] as T12)
            },
            createInvokeProxyAdapter = { proxy ->
                { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12 ->
                    proxy.invoke(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)
                }
            }
        )

}