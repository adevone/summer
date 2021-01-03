package summer.example.presentation

import kotlinx.coroutines.launch
import org.kodein.di.instance
import summer.example.domain.about.GetAbout
import summer.example.entity.About
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.LoadingView
import summer.example.presentation.base.loadingViewProxy
import summer.example.presentation.base.withLoading

interface AboutView : LoadingView {
    var about: About?
}

class AboutViewModel : BaseViewModel<AboutView, Unit>() {
    private val getAbout: GetAbout by instance()

    override val viewProxy: AboutView = object : AboutView,
        LoadingView by loadingViewProxy() {
        override var about by state({ it::about }, initial = null)
    }

    init {
        scope.launch {
            withLoading {
                val about = getAbout()
                viewProxy.about = about
            }
        }
    }

    override fun handle(input: Unit) {
        // do nothing
    }
}