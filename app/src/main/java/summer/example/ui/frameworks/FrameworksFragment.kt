package summer.example.ui.frameworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
        FrameworksView,
        FrameworksPresenter,
        FrameworksFragment.Args>(R.layout.frameworks_fragment) {

    override fun createPresenter() = FrameworksPresenter()

    override fun createViewState() = object : FrameworksView {

        override var items: List<Basket.Item> by didSet {
            frameworksAdapter.submitList(items)
        }

        override val toDetails = { framework: Framework ->
            ciceroneRouter.navigateTo(FrameworkDetailsFragment.Args(framework).toScreen())
        }
    }

    override val screenToolbar get() = toolbar!!

    private lateinit var frameworksAdapter: FrameworksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frameworksAdapter = FrameworksAdapter(presenter)
        frameworksView.adapter = frameworksAdapter
        (frameworksView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<FrameworksFragment>()
}