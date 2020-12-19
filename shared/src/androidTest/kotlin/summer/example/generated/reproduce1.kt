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
    mainViewModel.onMenuItemClick(
        tab = decode(""""Frameworks"""")
    )
    frameworksViewModel = createFrameworksViewModel()
    frameworksViewModel.getView = {
        createViewForFrameworksViewModel()
    }
    frameworksViewModel.viewCreated()
    frameworksViewModel.onIncreaseClick(
        framework = decode("""{"name":"Spring","version":"5.0"}""")
    )
    frameworksViewModel.onIncreaseClick(
        framework = decode("""{"name":"Spring","version":"5.0"}""")
    )
    frameworksViewModel.onIncreaseClick(
        framework = decode("""{"name":"Summer","version":"0.8.17"}""")
    )
    frameworksViewModel.onDecreaseClick(
        framework = decode("""{"name":"Summer","version":"0.8.17"}""")
    )
    frameworksViewModel.onCrashClick()
}

