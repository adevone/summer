package summer.example.generated

import summer.example.recording.decode

fun reproduce1(
    createMainViewModel: () -> summer.example.presentation.MainViewModel,
    createFrameworksViewModel: () -> summer.example.presentation.FrameworksViewModel,
    callOnFrameworkClickOfFrameworksViewModel: (summer.example.presentation.FrameworksViewModel, String) -> Unit,
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
        decode(""""Frameworks"""")
    )
    frameworksViewModel = createFrameworksViewModel()
    frameworksViewModel.getView = {
        createViewForFrameworksViewModel()
    }
    frameworksViewModel.viewCreated()
    frameworksViewModel.onIncreaseClick(
        decode("""{"name":"Spring","version":"5.0"}""")
    )
    frameworksViewModel.onIncreaseClick(
        decode("""{"name":"Spring","version":"5.0"}""")
    )
    frameworksViewModel.onIncreaseClick(
        decode("""{"name":"Summer","version":"0.8.17"}""")
    )
    frameworksViewModel.onDecreaseClick(
        decode("""{"name":"Summer","version":"0.8.17"}""")
    )
    frameworksViewModel.onDecreaseClick(
        decode("""{"name":"Summer","version":"0.8.17"}""")
    )
    callOnFrameworkClickOfFrameworksViewModel(
        frameworksViewModel,
        """{"name":"Summer","version":"0.8.17"}"""
    )
    frameworksViewModel.getView = { null }

    frameworksViewModel.getView = {
        createViewForFrameworksViewModel()
    }
    frameworksViewModel.viewCreated()
    frameworksViewModel.onCrashClick()
}

