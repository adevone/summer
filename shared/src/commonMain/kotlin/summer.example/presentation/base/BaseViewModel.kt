package summer.example.presentation.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import summer.SummerViewModel
import summer.ViewModelController
import summer.example.AppKodeinAware
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

abstract class BaseViewModel<TView> : SummerViewModel<TView>(), BaseViewModelController,
    AppKodeinAware,
    CoroutineScope {

    init {
        ViewModelEventsListener.onCreate(this::class)
    }

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = mainDispatcher + job

    override fun onDestroy() {
        job.cancel()
    }

    open fun onBackClick(): Boolean = false
}

expect object ViewModelEventsListener {
    fun onCreate(clazz: KClass<*>)
    fun onAttach(clazz: KClass<*>, viewClass: KClass<*>)
    fun onDetach(clazz: KClass<*>)
}

interface BaseViewModelController : ViewModelController {
    fun onDestroy() {}
}

expect val mainDispatcher: CoroutineDispatcher