package io.adev.summer.example.presentation

import io.adev.summer.example.entity.Framework
import io.adev.summer.example.entity.FullFramework
import io.adev.summer.example.entity.toFull
import io.adev.summer.example.presentation.base.BaseInput
import io.adev.summer.example.presentation.base.BaseViewModel
import kotlin.js.JsExport

@JsExport
interface FrameworkDetailsView {
    var framework: FullFramework?
    val notifyAboutName: (frameworkName: String) -> Unit
}

@JsExport
interface FrameworkDetailsInput : BaseInput<FrameworkDetailsView> {
    fun init(initialFramework: Framework)
}

class FrameworkDetailsViewModel : BaseViewModel<FrameworkDetailsView>(), FrameworkDetailsInput {

    override val viewProxy = object : FrameworkDetailsView {
        override var framework by state({ it::framework }, initial = null)
        override val notifyAboutName = event { it.notifyAboutName }.perform.exactlyOnce()
    }

    override fun init(initialFramework: Framework) {
        viewProxy.framework = initialFramework.toFull()
        viewProxy.notifyAboutName(initialFramework.name)
    }
}