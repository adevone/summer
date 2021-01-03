package io.adev.summer.example.presentation

import kotlinx.coroutines.launch
import org.kodein.di.instance
import io.adev.summer.example.domain.about.GetAbout
import io.adev.summer.example.entity.About
import io.adev.summer.example.presentation.base.BaseViewModel
import io.adev.summer.example.presentation.base.LoadingView
import io.adev.summer.example.presentation.base.loadingViewProxy
import io.adev.summer.example.presentation.base.withLoading

interface AboutView : LoadingView {
    var about: About?
}

class AboutViewModel : BaseViewModel<AboutView>() {
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
}