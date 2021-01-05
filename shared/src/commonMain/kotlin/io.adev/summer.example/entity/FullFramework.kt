package io.adev.summer.example.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class FullFramework(
    val name: String,
    val version: String
)

fun Framework.toFull() = FullFramework(name, version)