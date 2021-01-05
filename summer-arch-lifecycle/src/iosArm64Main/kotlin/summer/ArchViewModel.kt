package summer

import summer.events.EventPerformerFactory

actual abstract class ArchViewModel<TView> actual constructor() :
    DefaultSummerViewModel<TView>,
    ViewProxyProvider<TView>,
    SummerViewModel<TView>() {

    actual companion object : EventPerformerFactory()
}