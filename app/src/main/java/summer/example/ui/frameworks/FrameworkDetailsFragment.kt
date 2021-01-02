package summer.example.ui.frameworks

import android.widget.Toast
import kotlinx.serialization.Serializable
import summer.example.databinding.FrameworkDetailsFragmentBinding
import summer.example.entity.Framework
import summer.example.entity.FullFramework
import summer.example.presentation.FrameworkDetailsView
import summer.example.presentation.FrameworkDetailsViewModel
import summer.example.ui.base.BaseSaveInstanceStateFragment
import summer.example.ui.base.routing.ScreenArgs

class FrameworkDetailsFragment :
    BaseSaveInstanceStateFragment<FrameworkDetailsFragment.Args>(),
    FrameworkDetailsView {

    override val viewModel by bindViewModel {
        FrameworkDetailsViewModel(initialFramework = args.framework)
    }

    private val binding by viewBinding { FrameworkDetailsFragmentBinding.inflate(it) }

    override var framework: FullFramework? by didSet {
        binding.nameView.text = framework?.name ?: ""
        binding.versionView.text = framework?.version ?: ""
    }

    override var notifyAboutName = { frameworkName: String ->
        Toast.makeText(context, frameworkName, Toast.LENGTH_LONG).show()
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args(
        val framework: Framework
    ) : ScreenArgs<FrameworkDetailsFragment>(::FrameworkDetailsFragment)
}