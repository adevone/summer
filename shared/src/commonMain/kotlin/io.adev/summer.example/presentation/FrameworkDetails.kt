package io.adev.summer.example.presentation

import io.adev.summer.example.entity.Framework
import io.adev.summer.example.entity.FullFramework
import io.adev.summer.example.entity.toFull
import io.adev.summer.example.presentation.base.BaseViewModel

interface FrameworkDetailsView {
    var framework: FullFramework?
    val notifyAboutName: (frameworkName: String) -> Unit
}

class FrameworkDetailsViewModel : BaseViewModel<FrameworkDetailsView>() {

    override val viewProxy = object : FrameworkDetailsView {
        override var framework by state({ it::framework }, initial = null)
        override val notifyAboutName = event { it.notifyAboutName }.doOnlyWhenAttached()
    }

    fun init(initialFramework: Framework) {
        viewProxy.framework = initialFramework.toFull()
    }

    init {
        viewProxy.framework?.let { framework ->
            viewProxy.notifyAboutName(framework.name)
        }
    }
}