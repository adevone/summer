package io.adev.summer.example.entity

import kotlinx.serialization.Serializable

@Serializable
data class About(
    val frameworkName: String,
    val author: String,
    val logoUrl: String,
)