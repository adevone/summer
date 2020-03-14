package summer.example.ui.shoppingCart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.shopping_cart_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.Basket
import summer.example.presentation.ShoppingCartPresenter
import summer.example.presentation.ShoppingCartRouter
import summer.example.presentation.ShoppingCartView
import summer.example.ui.base.ScreenFragment
import summer.example.ui.base.routing.ScreenArgs

class ShoppingCartFragment : ScreenFragment<
        ShoppingCartView.State,
        ShoppingCartView.Methods,
        ShoppingCartRouter,
        ShoppingCartPresenter,
        ShoppingCartFragment.Args>() {

    override val router = object : ShoppingCartRouter {

    }

    override fun createViewState() = object : ShoppingCartView.State {

        override var items: List<Basket.Item> by didSet {
            shoppingCart.text = items.joinToString(separator = "\n") { item ->
                "${item.framework.name}=${item.quantity}"
            }
        }
    }

    override val viewMethods = object : ShoppingCartView.Methods {

    }

    override fun createPresenter() = ShoppingCartPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.shopping_cart_fragment,
        container,
        false
    )!!

    override val screenToolbar get() = toolbar!!

    override fun initView() {

    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<ShoppingCartFragment>()
}