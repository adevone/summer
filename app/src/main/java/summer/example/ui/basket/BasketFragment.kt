package summer.example.ui.basket

import android.os.Bundle
import kotlinx.serialization.Serializable
import summer.example.bindViewModel
import summer.example.databinding.BasketFragmentBinding
import summer.example.entity.Basket
import summer.example.presentation.BasketView
import summer.example.presentation.BasketViewModel
import summer.example.ui.base.BaseFragment
import summer.example.ui.base.routing.ScreenArgs

class BasketFragment : BaseFragment<BasketFragment.Args>(), BasketView {
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

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<BasketFragment>(::BasketFragment)
}