package summer.example.presentation

import summer.example.entity.Framework
import summer.example.entity.FullFramework
import summer.example.entity.toFull
import summer.example.presentation.base.BaseViewModel

interface FrameworkDetailsView {
    var framework: FullFramework?
    val notifyAboutName: (frameworkName: String) -> Unit
}

class FrameworkDetailsViewModel : BaseViewModel<FrameworkDetailsView>() {

    override val viewProxy = object : FrameworkDetailsView {
        override var framework by state({ it::framework }, initial = null)
        override val notifyAboutName = event { it.notifyAboutName }.doOnlyWhenAttached()
    }

    fun init(initialFramework: Framework) {
        viewProxy.framework = initialFramework.toFull()
    }

    init {
        viewProxy.framework?.let { framework ->
            viewProxy.notifyAboutName(framework.name)
        }
    }
}