package summer.example.ui.basket

import kotlinx.serialization.Serializable
import summer.example.databinding.BasketFragmentBinding
import summer.example.entity.Basket
import summer.example.presentation.BasketView
import summer.example.presentation.BasketViewModel
import summer.example.ui.base.BaseFragment
import summer.example.ui.base.routing.ScreenArgs

class BasketFragment : BaseFragment<BasketFragment.Args>(), BasketView {
    override val viewModel by bindViewModel { BasketViewModel() }
    private val binding by viewBinding { BasketFragmentBinding.inflate(it) }

    override var items: List<Basket.Item> by didSet {
        binding.basketView.text = items.joinToString(separator = "\n") { item ->
            "${item.framework.name}=${item.quantity}"
        }
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<BasketFragment>(::BasketFragment)
}