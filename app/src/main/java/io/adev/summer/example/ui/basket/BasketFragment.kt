package io.adev.summer.example.ui.basket

import android.os.Bundle
import io.adev.summer.example.bindViewModel
import io.adev.summer.example.databinding.BasketFragmentBinding
import io.adev.summer.example.entity.Basket
import io.adev.summer.example.presentation.BasketView
import io.adev.summer.example.presentation.BasketViewModel
import io.adev.summer.example.ui.base.BaseFragment

class BasketFragment : BaseFragment(), BasketView {
    private val binding by viewBinding { BasketFragmentBinding.inflate(it) }

    private lateinit var viewModel: BasketViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = bindViewModel(BasketViewModel::class, fragment = this) { this }
    }

    override var items: List<Basket.Item> by didSet {
        binding.basketView.text = items.joinToString(separator = "\n") { item ->
            "${item.framework.name}=${item.quantity}"
        }
    }

    companion object {
        fun newInstance() = BasketFragment()
    }
}