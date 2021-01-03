package summer.example.presentation.base

import kotlin.reflect.KClass

actual object ViewModelEventsListener {
    actual fun onCreate(clazz: KClass<*>) {}
    actual fun onAttach(clazz: KClass<*>, viewClass: KClass<*>) {}
    actual fun onDetach(clazz: KClass<*>) {}
}