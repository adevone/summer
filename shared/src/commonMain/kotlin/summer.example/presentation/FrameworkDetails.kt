package summer.example.presentation

import summer.example.entity.Framework
import summer.example.presentation.base.ScreenPresenter

interface FrameworkDetailsView {
    var framework: Framework?
    val notifyAboutName: (frameworkName: String) -> Unit
    val notifyAboutName2: (a: String, b: String) -> Unit
    val toMain: (isFromBasket: Boolean) -> Unit
}

class FrameworkDetailsPresenter(
    private val framework: Framework
) : ScreenPresenter<FrameworkDetailsView>() {

    override val viewProxy = object : FrameworkDetailsView {
        override var framework by store({ it::framework }, initial = null)
        override val notifyAboutName = event { it.notifyAboutName }.doOnlyWhenAttached()
        override val notifyAboutName2 = event { it.notifyAboutName2 }.repeatLast()
        override val toMain = event { it.toMain }.repeatOnlyOnce()
    }

    override fun onEnter() {
        super.onEnter()
        viewProxy.framework = framework
    }

    override fun onAppear() {
        super.onAppear()
        viewProxy.notifyAboutName(framework.name)
    }
}