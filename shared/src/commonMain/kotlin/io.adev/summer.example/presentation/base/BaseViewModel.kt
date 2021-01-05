package io.adev.summer.example.presentation.base

import kotlinx.coroutines.CoroutineScope
import summer.ArchViewModel

expect abstract class BaseViewModel<TView>() : ArchViewModel<TView> {
    val scope: CoroutineScope
}