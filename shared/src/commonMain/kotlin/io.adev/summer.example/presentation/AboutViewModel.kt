package io.adev.summer.example.presentation

import io.adev.summer.example.domain.about.GetAbout
import io.adev.summer.example.entity.About
import io.adev.summer.example.presentation.base.*
import kotlinx.coroutines.launch
import kotlin.js.JsExport

@JsExport
interface AboutView : LoadingView {
    var about: About?
}

@JsExport
interface AboutInput : BaseInput<AboutView>

class AboutViewModel(
    private val getAbout: GetAbout
) : BaseViewModel<AboutView>(), AboutInput {

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