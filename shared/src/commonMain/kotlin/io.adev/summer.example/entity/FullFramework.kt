package io.adev.summer.example.entity

import kotlinx.serialization.Serializable

@Serializable
data class FullFramework(
    val name: String,
    val version: String
)

fun Framework.toFull() = FullFramework(name, version)