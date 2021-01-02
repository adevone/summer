package summer.example.presentation

import kotlinx.coroutines.launch
import org.kodein.di.instance
import summer.example.domain.about.GetAbout
import summer.example.entity.About
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.LoadingView
import summer.example.presentation.base.withLoading

interface AboutView : LoadingView {
    var about: About?
}

class AboutViewModel : BaseViewModel<AboutView>() {
    private val getAbout: GetAbout by instance()

    override val viewProxy = object : AboutView {
        override var about by state({ it::about }, initial = null)
        override var isLoading by state({ it::isLoading }, initial = true)
    }

    override fun onEnter() {
        super.onEnter()
        viewModelScope.launch {
            withLoading {
                val about = getAbout()
                viewProxy.about = about
            }
        }
    }
}