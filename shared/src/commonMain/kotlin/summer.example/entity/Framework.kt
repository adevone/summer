package summer.example.entity

import kotlinx.serialization.Serializable

@Serializable
data class Framework(
    val name: String,
    val version: String
)