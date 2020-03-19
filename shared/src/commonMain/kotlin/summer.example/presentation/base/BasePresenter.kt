package summer.example.presentation.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import summer.SummerPresenter
import summer.example.AppKodeinAware
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<TView : Any> : SummerPresenter<TView>(),
    AppKodeinAware,
    CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = mainDispatcher + job

    open fun onAppear() {}
    open fun onDisappear() {}

    fun onDestroy() {
        job.cancel()
    }

    open fun onBackClick(): Boolean = false
}

expect val mainDispatcher: CoroutineDispatcher