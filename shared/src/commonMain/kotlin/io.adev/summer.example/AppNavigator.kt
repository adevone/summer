package io.adev.summer.example

import io.adev.summer.example.entity.Framework
import kotlin.js.JsExport

@JsExport
interface AppNavigator {
    fun toFrameworkDetails(framework: Framework)
}