package io.adev.summer.example.presentation.base

import kotlinx.coroutines.CoroutineScope
import summer.ArchViewModel
import io.adev.summer.example.AppKodeinAware

expect abstract class BaseViewModel<TView>() :
    ArchViewModel<TView>,
    AppKodeinAware {

    val scope: CoroutineScope
}