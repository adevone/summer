package summer.example

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import summer.example.domain.about.GetAbout
import summer.example.domain.basket.BasketController
import summer.example.domain.basket.CoroutinesBasketController
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.domain.frameworks.GetSpring
import summer.example.domain.frameworks.GetSummer

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