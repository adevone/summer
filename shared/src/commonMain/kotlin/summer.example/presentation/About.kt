package summer.example.presentation

import kotlinx.coroutines.launch
import org.kodein.di.erased.instance
import summer.example.domain.about.GetAbout
import summer.example.entity.About
import summer.example.presentation.base.LoadingViewState
import summer.example.presentation.base.ScreenPresenter
import summer.example.presentation.base.withLoading

object AboutView {

    interface State : LoadingViewState {
        var about: About?
    }

    interface Methods {

    }
}

interface AboutRouter

class AboutPresenter : ScreenPresenter<
        AboutView.State,
        AboutView.Methods,
        AboutRouter>() {

    private val getAbout: GetAbout by instance()

    override val viewStateProxy = object : AboutView.State {
        override var about by store({ it::about }, initial = null)
        override var isLoading by store({ it::isLoading }, initial = true)
    }

    override fun onAppear() {
        super.onAppear()
        launch {
            withLoading {
                val about = getAbout()
                viewStateProxy.about = about
            }
        }
    }
}