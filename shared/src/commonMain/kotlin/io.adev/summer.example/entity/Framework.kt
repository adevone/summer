package io.adev.summer.example.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class Framework(
    val name: String,
    val version: String
)