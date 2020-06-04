package summer.example.presentation.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import summer.PresenterController
import summer.SummerPresenter
import summer.example.AppKodeinAware
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<TView> : SummerPresenter<TView>(), BasePresenterController,
    AppKodeinAware,
    CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = mainDispatcher + job +
            CoroutineExceptionHandler { _, e ->
                println("Error occurred")
                println(e.message)
            }

    override fun onDestroy() {
        job.cancel()
    }

    open fun onBackClick(): Boolean = false
}

interface BasePresenterController : PresenterController {
    fun onDestroy() {}
}

expect val mainDispatcher: CoroutineDispatcher