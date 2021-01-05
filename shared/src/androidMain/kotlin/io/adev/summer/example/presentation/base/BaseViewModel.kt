package io.adev.summer.example.presentation.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import summer.ArchViewModel

actual abstract class BaseViewModel<TView> actual constructor() : ArchViewModel<TView>() {
    actual val scope: CoroutineScope = viewModelScope
}