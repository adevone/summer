package summer.example.presentation

import summer.example.entity.Framework
import summer.example.presentation.base.ScreenPresenter

object FrameworkDetailsView {

    interface State {
        var framework: Framework?
    }

    interface Methods {
        fun notifyAboutName(frameworkName: String)
    }
}

interface FrameworkDetailsRouter

class FrameworkDetailsPresenter(
    private val framework: Framework
) : ScreenPresenter<
        FrameworkDetailsView.State,
        FrameworkDetailsView.Methods,
        FrameworkDetailsRouter>() {

    override val viewStateProxy = object : FrameworkDetailsView.State {
        override var framework by store({ it::framework }, initial = null)
    }

    override fun onEnter() {
        super.onEnter()
        viewStateProxy.framework = framework
    }

    override fun onAppear() {
        super.onAppear()
        viewMethods?.notifyAboutName(framework.name)
    }
}