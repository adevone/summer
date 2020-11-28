package summer.example.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import summer.android.bundle.SaveStateSummerViewModel
import summer.example.presentation.base.mainDispatcher
import kotlin.coroutines.CoroutineContext

abstract class BaseSaveStateViewModel<TView> : SaveStateSummerViewModel<TView>(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = mainDispatcher + job

    fun onDestroy() {
        job.cancel()
    }

    open fun onBackClick(): Boolean = false
}