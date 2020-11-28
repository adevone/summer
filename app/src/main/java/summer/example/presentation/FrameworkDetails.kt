package summer.example.presentation

import summer.example.entity.Framework
import summer.example.entity.FullFramework
import summer.example.entity.toFull

interface FrameworkDetailsView {
    var framework: FullFramework?
    val notifyAboutName: (frameworkName: String) -> Unit
}

class FrameworkDetailsViewModel(
    private val initialFramework: Framework
) : BaseSaveStateViewModel<FrameworkDetailsView>() {

    override val viewProxy = object : FrameworkDetailsView {
        override var framework by state({ it::framework }, initial = initialFramework.toFull())
        override val notifyAboutName = event { it.notifyAboutName }.doOnlyWhenAttached()
    }

    override fun onEnter() {
        super.onEnter()
        viewProxy.notifyAboutName(initialFramework.name)
    }
}