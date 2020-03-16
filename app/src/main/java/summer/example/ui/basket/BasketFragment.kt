package summer.example.ui.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.basket_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.Basket
import summer.example.presentation.BasketPresenter
import summer.example.presentation.BasketView
import summer.example.ui.base.ScreenFragment
import summer.example.ui.base.routing.ScreenArgs

class BasketFragment : ScreenFragment<
        BasketView,
        BasketPresenter,
        BasketFragment.Args>(R.layout.basket_fragment) {

    override fun createViewState() = object : BasketView {

        override var items: List<Basket.Item> by didSet {
            basketView.text = items.joinToString(separator = "\n") { item ->
                "${item.framework.name}=${item.quantity}"
            }
        }
    }

    override fun createPresenter() = BasketPresenter()

    override val screenToolbar get() = toolbar!!

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<BasketFragment>()
}