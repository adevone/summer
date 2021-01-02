package summer.example.presentation.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import summer.SummerViewModel
import summer.ViewModelController
import summer.example.AppKodeinAware
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.ThreadLocal

abstract class BaseViewModel<TView> : SummerViewModel<TView>(), BaseViewModelController,
    AppKodeinAware {

    private val job = SupervisorJob()
    private val coroutineContext: CoroutineContext = mainDispatcher + job
    val viewModelScope = CoroutineScope(coroutineContext)

    override fun onDestroy() {
        job.cancel()
    }

    open fun onBackClick(): Boolean = false
}

interface BaseViewModelController : ViewModelController {
    fun onDestroy() {}
}

expect val mainDispatcher: CoroutineDispatcher