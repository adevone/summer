package summer.example

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import summer.example.domain.about.GetAbout
import summer.example.domain.basket.BasketController
import summer.example.domain.basket.CoroutinesBasketController
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.domain.frameworks.GetSpring
import summer.example.domain.frameworks.GetSummer

lateinit var di: Kodein

@ExperimentalCoroutinesApi
@FlowPreview
val sharedModule = Kodein.Module("shared") {

    // frameworks
    bind<GetAllFrameworkItems>() with singleton { GetAllFrameworkItems(instance(), instance(), instance()) }
    bind() from singleton { GetSpring() }
    bind() from singleton { GetSummer() }

    // about
    bind() from singleton { GetAbout(instance(), instance()) }

    // basket
    bind<BasketController>() with singleton { CoroutinesBasketController() }
}