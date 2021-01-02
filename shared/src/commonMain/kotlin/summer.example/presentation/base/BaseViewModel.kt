package summer.example.presentation.base

import kotlinx.coroutines.CoroutineScope
import summer.ArchViewModel
import summer.example.AppKodeinAware

expect abstract class BaseViewModel<TView>() :
    ArchViewModel<TView>,
    AppKodeinAware {

    val scope: CoroutineScope
}