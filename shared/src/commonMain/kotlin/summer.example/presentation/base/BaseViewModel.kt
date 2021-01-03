package summer.example.presentation.base

import kotlin.reflect.KClass

abstract class BaseViewModel<TView> : CoroutinesViewModel<TView>() {

    init {
        ViewModelEventsListener.onCreate(this::class)
    }
}

expect object ViewModelEventsListener {
    fun onCreate(clazz: KClass<*>)
    fun onAttach(clazz: KClass<*>, viewClass: KClass<*>)
    fun onDetach(clazz: KClass<*>)
}