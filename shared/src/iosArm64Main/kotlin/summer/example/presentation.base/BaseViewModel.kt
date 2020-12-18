package summer.example.presentation.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.KClass

actual val mainDispatcher: CoroutineDispatcher = Dispatchers.Unconfined

actual object ViewModelEventsListener {
    actual fun onCreate(clazz: KClass<*>) {}
    actual fun onAttach(clazz: KClass<*>, viewClass: KClass<*>) {}
    actual fun onDetach(clazz: KClass<*>) {}
}