package summer.example.ui.frameworks

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.frameworks_fragment.*
import kotlinx.serialization.Serializable
import org.kodein.di.direct
import org.kodein.di.instance
import summer.example.R
import summer.example.entity.Basket
import summer.example.entity.Framework
import summer.example.presentation.FrameworksView
import summer.example.presentation.FrameworksViewModel
import summer.example.ui.base.BaseFragment
import summer.example.ui.base.routing.ScreenArgs
import summer.example.ui.base.routing.toScreen

class FrameworksFragment :
    BaseFragment<FrameworksFragment.Args>(R.layout.frameworks_fragment),
    FrameworksView {

    override val viewModel by bindViewModel {
        FrameworksViewModel(
            basketController = di.direct.instance(),
            getAllFrameworkItems = di.direct.instance()
        )
    }

    override var items: List<Basket.Item> by didSet {
        frameworksAdapter.submitList(items)
    }

    override val toDetails = { framework: Framework ->
        ciceroneRouter.navigateTo(FrameworkDetailsFragment.Args(framework).toScreen())
    }

    private lateinit var frameworksAdapter: FrameworksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        frameworksAdapter = FrameworksAdapter(viewModel)
        frameworksView.adapter = frameworksAdapter
        (frameworksView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        crashButton.setOnClickListener {
            viewModel.onCrashClick()
        }
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<FrameworksFragment>(::FrameworksFragment)
}