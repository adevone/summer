package io.adev.summer.example

import io.adev.summer.example.domain.about.GetAbout
import io.adev.summer.example.domain.basket.BasketController
import io.adev.summer.example.domain.basket.CoroutinesBasketController
import io.adev.summer.example.domain.frameworks.GetAllFrameworkItems
import io.adev.summer.example.domain.frameworks.GetSpring
import io.adev.summer.example.domain.frameworks.GetSummer
import io.adev.summer.example.presentation.*
import io.ktor.client.*
import kotlinx.serialization.json.Json
import kotlin.js.JsExport

@JsExport
object ServiceLocator {

    private val json = Json {
        encodeDefaults = false
        ignoreUnknownKeys = true
    }

    private val basketController: BasketController = CoroutinesBasketController()

    fun aboutViewModel(): AboutInput = AboutViewModel(GetAbout(PlatformServices.httpClient(), json))

    fun basketViewModel(): BasketInput = BasketViewModel(basketController)

    fun frameworkDetailsViewModel(): FrameworkDetailsInput = FrameworkDetailsViewModel()

    fun frameworksViewModel(): FrameworksInput = FrameworksViewModel(
        basketController,
        GetAllFrameworkItems(GetSpring(), GetSummer(), basketController)
    )

//    fun mainViewModel() = MainViewModel()
}

internal expect object PlatformServices {
    fun httpClient(): HttpClient
}