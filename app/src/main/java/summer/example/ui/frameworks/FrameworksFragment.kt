package summer.example.ui.frameworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.frameworks_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.FrameworksPresenter
import summer.example.presentation.FrameworksRouter
import summer.example.presentation.FrameworksView
import summer.example.ui.base.ScreenFragment
import summer.example.ui.base.routing.ScreenArgs
import summer.example.ui.base.routing.toScreen

class FrameworksFragment : ScreenFragment<
        FrameworksView.State,
        FrameworksView.Methods,
        FrameworksRouter,
        FrameworksPresenter,
        FrameworksFragment.Args>() {

    override val router = object : FrameworksRouter {

        override fun toDetails(framework: Framework) {
            ciceroneRouter.navigateTo(FrameworkDetailsFragment.Args(framework).toScreen())
        }
    }

    override fun createPresenter() = FrameworksPresenter()

    override fun createViewState() = object : FrameworksView.State {

        override var items: List<Basket.Item> by didSet {
            frameworksAdapter.submitList(items)
        }
    }

    override val viewMethods = object : FrameworksView.Methods {

    }

    override val screenToolbar get() = toolbar!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.frameworks_fragment,
        container,
        false
    )!!

    private lateinit var frameworksAdapter: FrameworksAdapter

    override fun initView() {
        super.initView()
        frameworksAdapter = FrameworksAdapter(presenter)
        frameworksView.adapter = frameworksAdapter
        (frameworksView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<FrameworksFragment>()
}