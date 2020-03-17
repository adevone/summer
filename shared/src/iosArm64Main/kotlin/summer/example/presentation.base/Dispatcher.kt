package summer.example.presentation.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val mainDispatcher: CoroutineDispatcher = Dispatchers.Unconfined