package summer.example.ui.frameworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.framework_details_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.Framework
import summer.example.presentation.FrameworkDetailsPresenter
import summer.example.presentation.FrameworkDetailsRouter
import summer.example.presentation.FrameworkDetailsView
import summer.example.ui.base.ScreenFragment
import summer.example.ui.base.routing.ScreenArgs

class FrameworkDetailsFragment : ScreenFragment<
        FrameworkDetailsView.State,
        FrameworkDetailsView.Methods,
        FrameworkDetailsRouter,
        FrameworkDetailsPresenter,
        FrameworkDetailsFragment.Args>() {

    override val router = object : FrameworkDetailsRouter {}

    override fun createPresenter() = FrameworkDetailsPresenter(
        framework = args.framework
    )

    override fun createViewState() = object : FrameworkDetailsView.State {

        override var framework: Framework? by didSet {
            nameView.text = framework?.name ?: ""
            versionView.text = framework?.version ?: ""
        }
    }

    override val viewMethods = object : FrameworkDetailsView.Methods {

        override fun notifyAboutName(frameworkName: String) {
            Toast.makeText(context, frameworkName, Toast.LENGTH_LONG).show()
        }
    }

    override val screenToolbar get() = toolbar!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.framework_details_fragment,
        container,
        false
    )!!

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args(
        val framework: Framework
    ) : ScreenArgs<FrameworkDetailsFragment>()
}