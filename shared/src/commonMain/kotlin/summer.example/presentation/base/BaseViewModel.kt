package summer.example.presentation.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import summer.SummerViewModel
import summer.ViewModelController
import summer.example.AppKodeinAware
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<TView> : SummerViewModel<TView>(), BaseViewModelController,
    AppKodeinAware,
    CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = mainDispatcher + job

    override fun onDestroy() {
        job.cancel()
    }

    open fun onBackClick(): Boolean = false
}

interface BaseViewModelController : ViewModelController {
    fun onDestroy() {}
}

expect val mainDispatcher: CoroutineDispatcher