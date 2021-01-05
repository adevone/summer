package io.adev.summer.example.presentation.base

import io.adev.summer.example.AppKodeinAware
import kotlinx.coroutines.CoroutineScope
import summer.ArchViewModel

expect abstract class BaseViewModel<TView>() :
    ArchViewModel<TView>,
    AppKodeinAware {

    val scope: CoroutineScope
}