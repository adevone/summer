package summer.example.generated

import summer.example.recording.decode

fun reproduce1(
    createMainViewModel: () -> summer.example.presentation.MainViewModel,
    createFrameworksViewModel: () -> summer.example.presentation.FrameworksViewModel,
    createFrameworkDetailsViewModel: () -> summer.example.presentation.FrameworkDetailsViewModel,
    callOnItemClickOfFrameworksViewModel: (summer.example.presentation.FrameworksViewModel, item: String) -> Unit,
    createViewForMainViewModel: () -> summer.example.presentation.MainView? = { null },
    createViewForFrameworksViewModel: () -> summer.example.presentation.FrameworksView? = { null },
    createViewForFrameworkDetailsViewModel: () -> summer.example.presentation.FrameworkDetailsView? = { null }
) {
    lateinit var mainViewModel: summer.example.presentation.MainViewModel
    lateinit var frameworksViewModel: summer.example.presentation.FrameworksViewModel
    lateinit var frameworkDetailsViewModel: summer.example.presentation.FrameworkDetailsViewModel

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
        item = decode("""{"framework":{"name":"Spring","version":"5.0"},"quantity":0}""")
    )
    frameworksViewModel.onIncreaseClick(
        item = decode("""{"framework":{"name":"Spring","version":"5.0"},"quantity":1}""")
    )
    frameworksViewModel.onIncreaseClick(
        item = decode("""{"framework":{"name":"Summer","version":"0.8.17"},"quantity":0}""")
    )
    frameworksViewModel.onDecreaseClick(
        item = decode("""{"framework":{"name":"Summer","version":"0.8.17"},"quantity":1}""")
    )
    callOnItemClickOfFrameworksViewModel(
        frameworksViewModel,
        """{"framework":{"name":"Summer","version":"0.8.17"},"quantity":0}"""
    )
    frameworksViewModel.getView = { null }

    frameworkDetailsViewModel = createFrameworkDetailsViewModel()
    frameworkDetailsViewModel.getView = {
        createViewForFrameworkDetailsViewModel()
    }
    frameworkDetailsViewModel.viewCreated()
    frameworkDetailsViewModel.init(
        initialFramework = decode("""{"name":"Summer","version":"0.8.17"}""")
    )
    frameworkDetailsViewModel.getView = { null }

    frameworksViewModel.getView = {
        createViewForFrameworksViewModel()
    }
    frameworksViewModel.viewCreated()
    frameworksViewModel.onCrashClick()
}

