package io.adev.summer.example

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import io.adev.summer.example.domain.about.GetAbout
import io.adev.summer.example.domain.basket.BasketController
import io.adev.summer.example.domain.basket.CoroutinesBasketController
import io.adev.summer.example.domain.frameworks.GetAllFrameworkItems
import io.adev.summer.example.domain.frameworks.GetSpring
import io.adev.summer.example.domain.frameworks.GetSummer

lateinit var mainDI: DI

val sharedModule = DI.Module("shared") {

    // frameworks
    bind<GetAllFrameworkItems>() with singleton { GetAllFrameworkItems(instance(), instance(), instance()) }
    bind() from singleton { GetSpring() }
    bind() from singleton { GetSummer() }

    // about
    bind() from singleton { GetAbout(instance(), instance()) }

    // basket
    bind<BasketController>() with singleton { CoroutinesBasketController() }
}