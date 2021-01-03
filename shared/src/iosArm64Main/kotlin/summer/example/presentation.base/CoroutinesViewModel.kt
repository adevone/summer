package summer.example.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import summer.ArchViewModel
import summer.ViewModelController
import summer.example.AppKodeinAware
import kotlin.coroutines.CoroutineContext

actual abstract class CoroutinesViewModel<TView> actual constructor() :
    ArchViewModel<TView>(),
    AppKodeinAware,
    BaseViewModelController {

    private val job = SupervisorJob()
    private val coroutineContext: CoroutineContext = Dispatchers.Main + job
    actual val scope = CoroutineScope(coroutineContext)

    override fun onDestroy() {
        job.cancel()
    }
}

interface BaseViewModelController : ViewModelController {
    fun onDestroy()
}