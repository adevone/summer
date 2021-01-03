package summer.example.presentation

import kotlinx.serialization.Serializable
import summer.example.entity.Framework
import summer.example.entity.FullFramework
import summer.example.entity.toFull
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.exhaustive

interface FrameworkDetailsView {
    var framework: FullFramework?
    val notifyAboutName: (frameworkName: String) -> Unit
}

sealed class FrameworkDetailsInput {

    @Serializable
    data class Init(val initialFramework: Framework) : FrameworkDetailsInput()
}

class FrameworkDetailsViewModel : BaseViewModel<FrameworkDetailsView, FrameworkDetailsInput>() {

    override val viewProxy = object : FrameworkDetailsView {
        override var framework by state({ it::framework }, initial = null)
        override val notifyAboutName = event { it.notifyAboutName }.doOnlyWhenAttached()
    }

    init {
        viewProxy.framework?.let { framework ->
            viewProxy.notifyAboutName(framework.name)
        }
    }

    override fun handle(input: FrameworkDetailsInput) {
        when (input) {
            is FrameworkDetailsInput.Init -> {
                viewProxy.framework = input.initialFramework.toFull()
            }
        }.exhaustive
    }
}