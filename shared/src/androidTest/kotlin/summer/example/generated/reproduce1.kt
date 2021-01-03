package summer.example.generated

import summer.example.recording.decode

fun reproduce1(
    createMainViewModel: () -> summer.example.presentation.MainViewModel,
    createFrameworksViewModel: () -> summer.example.presentation.FrameworksViewModel,
    createViewForMainViewModel: () -> summer.example.presentation.MainView? = { null },
    createViewForFrameworksViewModel: () -> summer.example.presentation.FrameworksView? = { null }
) {
    lateinit var mainViewModel: summer.example.presentation.MainViewModel
    lateinit var frameworksViewModel: summer.example.presentation.FrameworksViewModel

    mainViewModel = createMainViewModel()
    mainViewModel.getView = {
        createViewForMainViewModel()
    }
    mainViewModel.viewCreated()
    mainViewModel.pass(
        decode<summer.example.presentation.MainInput.MenuItemClicked>("""{"tab":"Frameworks"}""")
    )

    frameworksViewModel = createFrameworksViewModel()
    frameworksViewModel.getView = {
        createViewForFrameworksViewModel()
    }
    frameworksViewModel.viewCreated()
    frameworksViewModel.pass(
        decode<summer.example.presentation.FrameworksInput.IncreaseClicked>("""{"item":{"framework":{"name":"Spring","version":"5.0"},"quantity":0}}""")
    )

    frameworksViewModel.pass(
        decode<summer.example.presentation.FrameworksInput.IncreaseClicked>("""{"item":{"framework":{"name":"Spring","version":"5.0"},"quantity":1}}""")
    )

    frameworksViewModel.pass(
        decode<summer.example.presentation.FrameworksInput.IncreaseClicked>("""{"item":{"framework":{"name":"Summer","version":"0.8.17"},"quantity":0}}""")
    )

    frameworksViewModel.pass(
        decode<summer.example.presentation.FrameworksInput.DecreaseClicked>("""{"item":{"framework":{"name":"Summer","version":"0.8.17"},"quantity":1}}""")
    )

    frameworksViewModel.pass(
        decode<summer.example.presentation.FrameworksInput.CrashClicked>("""{}""")
    )

}

