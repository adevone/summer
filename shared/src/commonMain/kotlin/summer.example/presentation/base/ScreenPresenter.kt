package summer.example.presentation.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import summer.SummerPresenter
import summer.example.AppKodeinAware
import kotlin.coroutines.CoroutineContext

abstract class xScreenPresenter<TView : Any> : SummerPresenter<TView>(),
    AppKodeinAware,
    CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = mainDispatcher + job

    override fun destroyed() {
        super.destroyed()
        job.cancel()
    }

    open fun onBackClick(): Boolean = false
}

expect val mainDispatcher: CoroutineDispatcher