package summer.example.presentation

import summer.example.entity.Framework
import summer.example.presentation.base.BasePresenter

interface FrameworkDetailsView {
    var framework: Framework?
    val notifyAboutName: (frameworkName: String) -> Unit
}

class FrameworkDetailsPresenter(
    private val initialFramework: Framework
) : BasePresenter<FrameworkDetailsView>() {

    override val viewProxy = object : FrameworkDetailsView {
        override var framework by state({ it::framework }, initial = initialFramework)
        override val notifyAboutName = event { it.notifyAboutName }.doOnlyWhenAttached()
    }

    override fun onEnter() {
        super.onEnter()
        viewProxy.notifyAboutName(initialFramework.name)
    }
}