package io.adev.summer.example.presentation.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import summer.ArchViewModel
import io.adev.summer.example.AppKodeinAware

actual abstract class BaseViewModel<TView> actual constructor() :
    ArchViewModel<TView>(),
    AppKodeinAware {

    actual val scope: CoroutineScope = viewModelScope
}