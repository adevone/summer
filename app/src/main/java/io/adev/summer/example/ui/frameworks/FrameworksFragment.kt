package io.adev.summer.example.ui.frameworks

import android.os.Bundle
import androidx.recyclerview.widget.SimpleItemAnimator
import io.adev.summer.example.bindViewModel
import io.adev.summer.example.databinding.FrameworksFragmentBinding
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.entity.BasketItem
import io.adev.summer.example.presentation.FrameworksView
import io.adev.summer.example.presentation.FrameworksViewModel
import io.adev.summer.example.ui.base.BaseFragment

class FrameworksFragment : BaseFragment(), FrameworksView {

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

    override var items: Array<BasketItem> by didSet {
        frameworksAdapter.submitList(items.toList())
    }

    companion object {
        fun newInstance() = FrameworksFragment()
    }
}