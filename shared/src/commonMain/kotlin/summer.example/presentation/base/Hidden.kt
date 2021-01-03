package summer.example.presentation.base

import kotlinx.serialization.Serializable

@Serializable
data class Hidden<T>(
    val value: T,
)
