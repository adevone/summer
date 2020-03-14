package summer.example

import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import summer.example.domain.about.GetAbout
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.domain.frameworks.GetSpring
import summer.example.domain.frameworks.GetSummer
import summer.example.domain.shoppingCart.BasketHolder

lateinit var di: Kodein

val sharedModule = Kodein.Module("shared") {

    // frameworks
    bind<GetAllFrameworkItems>() with singleton { GetAllFrameworkItems(instance(), instance(), instance()) }
    bind<GetSpring>() with singleton { GetSpring() }
    bind<GetSummer>() with singleton { GetSummer() }

    // about
    bind<GetAbout>() with singleton { GetAbout(instance(), instance()) }

    // basket
    bind<BasketHolder>() with singleton { BasketHolder() }
}