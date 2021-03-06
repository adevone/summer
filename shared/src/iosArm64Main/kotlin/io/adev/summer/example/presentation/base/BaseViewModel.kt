package io.adev.summer.example.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import summer.arch.ArchViewModel
import summer.ViewModelController
import kotlin.coroutines.CoroutineContext

actual abstract class BaseViewModel<TView> actual constructor() :
    ArchViewModel<TView>(),
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