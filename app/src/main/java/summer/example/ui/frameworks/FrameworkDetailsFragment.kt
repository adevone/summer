package summer.example.ui.frameworks

import android.widget.Toast
import kotlinx.android.synthetic.main.framework_details_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.Framework
import summer.example.presentation.FrameworkDetailsPresenter
import summer.example.presentation.FrameworkDetailsView
import summer.example.ui.base.ScreenFragment
import summer.example.ui.base.routing.ScreenArgs

class FrameworkDetailsFragment : ScreenFragment<
        FrameworkDetailsView,
        FrameworkDetailsPresenter,
        FrameworkDetailsFragment.Args>(R.layout.framework_details_fragment) {

    override fun createPresenter() = FrameworkDetailsPresenter(
        framework = args.framework
    )

    override fun createViewState() = object : FrameworkDetailsView {

        override var framework: Framework? by didSet {
            nameView.text = framework?.name ?: ""
            versionView.text = framework?.version ?: ""
        }

        override var notifyAboutName = { frameworkName: String ->
            Toast.makeText(context, frameworkName, Toast.LENGTH_LONG).show()
        }

        override val notifyAboutName2 = { a: String, b: String ->
            Toast.makeText(context, a + b, Toast.LENGTH_LONG).show()
        }
    }

    override val screenToolbar get() = toolbar!!

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args(
        val framework: Framework
    ) : ScreenArgs<FrameworkDetailsFragment>()
}