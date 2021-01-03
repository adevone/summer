package summer.example.presentation.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import summer.ArchViewModel
import summer.example.AppKodeinAware
import summer.example.recording.InputStep
import summer.example.recording.steps
import kotlin.reflect.KClass

actual abstract class CoroutinesViewModel<TView> actual constructor() :
    ArchViewModel<TView>(),
    AppKodeinAware {

    actual val scope: CoroutineScope = viewModelScope
}