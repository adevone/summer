package summer.example.ui.basket

import kotlinx.android.synthetic.main.basket_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.Basket
import summer.example.presentation.BasketPresenter
import summer.example.presentation.BasketView
import summer.example.ui.base.ScreenFragment
import summer.example.ui.base.routing.ScreenArgs

class BasketFragment : ScreenFragment<BasketFragment.Args>(R.layout.basket_fragment), BasketView {

    override val presenter by summerPresenter { BasketPresenter() }

    override var items: List<Basket.Item> by didSet {
        basketView.text = items.joinToString(separator = "\n") { item ->
            "${item.framework.name}=${item.quantity}"
        }
    }

    override val screenToolbar get() = toolbar!!

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<BasketFragment>()
}