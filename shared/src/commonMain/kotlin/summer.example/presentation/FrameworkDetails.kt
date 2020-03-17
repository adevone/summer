package summer.example.presentation

import summer.example.entity.Framework
import summer.example.presentation.base.ScreenPresenter

interface FrameworkDetailsView {
    var framework: Framework?
    val notifyAboutName: (frameworkName: String) -> Unit
}

class FrameworkDetailsPresenter(
    private val initialFramework: Framework
) : ScreenPresenter<FrameworkDetailsView>() {

    override val viewProxy = object : FrameworkDetailsView {
        override var framework by state({ it::framework }, initial = null)
        override val notifyAboutName = event { it.notifyAboutName }.doOnlyWhenAttached()
    }

    override fun onEnter() {
        super.onEnter()
        viewProxy.framework = initialFramework
    }

    override fun onAppear() {
        super.onAppear()
        viewProxy.notifyAboutName(initialFramework.name)
    }
}