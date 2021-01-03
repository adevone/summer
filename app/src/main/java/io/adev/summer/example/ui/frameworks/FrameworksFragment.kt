package io.adev.summer.example.ui.frameworks

import android.os.Bundle
import androidx.recyclerview.widget.SimpleItemAnimator
import io.adev.summer.example.bindViewModel
import io.adev.summer.example.databinding.FrameworksFragmentBinding
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.entity.Framework
import io.adev.summer.example.presentation.FrameworksView
import io.adev.summer.example.presentation.FrameworksViewModel
import io.adev.summer.example.ui.base.BaseFragment
import io.adev.summer.example.ui.base.routing.ScreenArgs
import io.adev.summer.example.ui.base.routing.toScreen
import kotlinx.serialization.Serializable

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