package summer.example.presentation.base

import kotlinx.coroutines.CoroutineScope
import summer.ArchViewModel
import summer.example.AppKodeinAware

expect abstract class CoroutinesViewModel<TView>() :
    ArchViewModel<TView>,
    AppKodeinAware {

    val scope: CoroutineScope
}