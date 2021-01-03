package summer.example.generated

import summer.example.recording.decode

fun reproduce1(
    createMainViewModel: () -> summer.example.presentation.MainViewModel,
    createFrameworksViewModel: () -> summer.example.presentation.FrameworksViewModel
) {
    lateinit var mainViewModel: summer.example.presentation.MainViewModel
    lateinit var frameworksViewModel: summer.example.presentation.FrameworksViewModel

    mainViewModel.pass(
        decode("""{"tab":"Frameworks"}""")
    )

    frameworksViewModel.pass(
        decode("""{"item":{"framework":{"name":"Spring","version":"5.0"},"quantity":0}}""")
    )

    frameworksViewModel.pass(
        decode("""{"item":{"framework":{"name":"Spring","version":"5.0"},"quantity":1}}""")
    )

    frameworksViewModel.pass(
        decode("""{"item":{"framework":{"name":"Summer","version":"0.8.17"},"quantity":0}}""")
    )

    frameworksViewModel.pass(
        decode("""{"item":{"framework":{"name":"Summer","version":"0.8.17"},"quantity":1}}""")
    )

    frameworksViewModel.pass(
        decode("""{}""")
    )

}

