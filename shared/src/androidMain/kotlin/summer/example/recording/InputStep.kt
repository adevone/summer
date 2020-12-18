package summer.example.recording

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.full.createType

inline fun <reified T> decode(json: String): T {
    val argSerializer = serializer(T::class.createType()) as KSerializer<T>
    return Json.decodeFromString(argSerializer, json)
}