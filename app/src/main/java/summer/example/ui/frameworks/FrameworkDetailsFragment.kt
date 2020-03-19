package summer.example.ui.frameworks

import android.widget.Toast
import kotlinx.android.synthetic.main.framework_details_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.Framework
import summer.example.presentation.FrameworkDetailsPresenter
import summer.example.presentation.FrameworkDetailsView
import summer.example.ui.base.BaseFragment
import summer.example.ui.base.routing.ScreenArgs

class FrameworkDetailsFragment :
    BaseFragment<FrameworkDetailsFragment.Args>(R.layout.framework_details_fragment),
    FrameworkDetailsView {

    override var framework: Framework? by didSet {
        nameView.text = framework?.name ?: ""
        versionView.text = framework?.version ?: ""
    }

    override var notifyAboutName = { frameworkName: String ->
        Toast.makeText(context, frameworkName, Toast.LENGTH_LONG).show()
    }

    override val presenter by summerPresenter {
        FrameworkDetailsPresenter(initialFramework = args.framework)
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args(
        val framework: Framework
    ) : ScreenArgs<FrameworkDetailsFragment>(::FrameworkDetailsFragment)
}