package io.adev.summer.example

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

internal actual object PlatformServices {
    actual fun httpClient(): HttpClient = HttpClient(OkHttp)
}