package summer.example

import kotlinx.coroutines.Dispatchers
import summer.example.domain.basket.CoroutinesBasketController
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.domain.frameworks.GetSpring
import summer.example.domain.frameworks.GetSummer
import summer.example.generated.reproduce1
import summer.example.presentation.FrameworksViewModel
import summer.example.presentation.MainViewModel
import summer.example.presentation.base.Hidden
import summer.example.presentation.base.mainDispatcher
import summer.example.recording.decode
import kotlin.test.BeforeTest
import kotlin.test.Test

class ReproduceTests {

    @BeforeTest
    fun initDispatcher() {
        mainDispatcher = Dispatchers.Unconfined
    }

    @Test
    fun case1() {
        val basketController = CoroutinesBasketController()

        reproduce1(
            createMainViewModel = {
                MainViewModel()
            },
            createFrameworksViewModel = {
                FrameworksViewModel(
                    basketController = basketController,
                    getAllFrameworkItems = GetAllFrameworkItems(GetSpring(), GetSummer(), basketController)
                )
            },
            callOnFrameworkClickOfFrameworksViewModel = { viewModel, framework ->
                viewModel.onFrameworkClick(
                    Hidden("123"),
                    decode(framework)
                )
            }
        )
    }
}

