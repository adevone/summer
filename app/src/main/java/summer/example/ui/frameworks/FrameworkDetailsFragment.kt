package summer.example.ui.frameworks

import android.os.Bundle
import android.widget.Toast
import kotlinx.serialization.Serializable
import summer.example.databinding.FrameworkDetailsFragmentBinding
import summer.example.entity.Framework
import summer.example.entity.FullFramework
import summer.example.presentation.FrameworkDetailsView
import summer.example.presentation.FrameworkDetailsViewModel
import summer.example.ui.base.BaseFragment
import summer.example.ui.base.routing.ScreenArgs

class FrameworkDetailsFragment :
    BaseFragment<FrameworkDetailsFragment.Args>(),
    FrameworkDetailsView {

    private val binding by viewBinding { FrameworkDetailsFragmentBinding.inflate(it) }

    private lateinit var viewModel: FrameworkDetailsViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = bindViewModel(FrameworkDetailsViewModel::class, fragment = this) { this }
        viewModel.init(initialFramework = args.framework)
    }

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