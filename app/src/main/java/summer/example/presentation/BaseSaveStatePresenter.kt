package summer.example.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import summer.android.bundle.SaveStateSummerPresenter
import summer.example.presentation.base.mainDispatcher
import kotlin.coroutines.CoroutineContext

abstract class BaseSaveStatePresenter<TView> : SaveStateSummerPresenter<TView>(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = mainDispatcher + job

    fun onDestroy() {
        job.cancel()
    }

    open fun onBackClick(): Boolean = false
}