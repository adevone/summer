package summer.example

import summer.example.domain.basket.CoroutinesBasketController
import summer.example.domain.frameworks.GetAllFrameworkItems
import summer.example.domain.frameworks.GetSpring
import summer.example.domain.frameworks.GetSummer
import summer.example.generated.reproduce1
import summer.example.presentation.FrameworksViewModel
import summer.example.presentation.MainViewModel
import summer.example.recording.decode
import kotlin.test.Test

class ReproduceTests {

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
                    password = "123",
                    framework = decode(framework)
                )
            }
        )
    }
}

