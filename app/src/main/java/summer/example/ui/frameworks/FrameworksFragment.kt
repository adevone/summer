package summer.example.ui.frameworks

import android.os.Bundle
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.serialization.Serializable
import summer.example.databinding.FrameworksFragmentBinding
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.FrameworksInput
import summer.example.presentation.FrameworksView
import summer.example.presentation.FrameworksViewModel
import summer.example.ui.base.BaseFragment
import summer.example.ui.base.routing.ScreenArgs
import summer.example.ui.base.routing.toScreen

class FrameworksFragment :
    BaseFragment<FrameworksFragment.Args>(),
    FrameworksView {

    private val binding by viewBinding { FrameworksFragmentBinding.inflate(it) }

    private lateinit var viewModel: FrameworksViewModel

    private lateinit var frameworksAdapter: FrameworksAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = bindViewModel(FrameworksViewModel::class, fragment = this) { this }

        frameworksAdapter = FrameworksAdapter(viewModel)
        binding.frameworksView.adapter = frameworksAdapter
        (binding.frameworksView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.crashButton.setOnClickListener {
            viewModel.pass(FrameworksInput.CrashClicked)
        }
    }

    override var items: List<Basket.Item> by didSet {
        frameworksAdapter.submitList(items)
    }

    override val toDetails = { framework: Framework ->
        ciceroneRouter.navigateTo(FrameworkDetailsFragment.Args(framework).toScreen())
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<FrameworksFragment>(::FrameworksFragment)
}