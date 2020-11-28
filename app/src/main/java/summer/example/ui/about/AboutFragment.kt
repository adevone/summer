package summer.example.ui.about

import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.about_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.About
import summer.example.presentation.AboutViewModel
import summer.example.presentation.AboutView
import summer.example.ui.base.BaseFragment
import summer.example.ui.base.routing.ScreenArgs

class AboutFragment : BaseFragment<AboutFragment.Args>(R.layout.about_fragment), AboutView {

    override val viewModel by bindViewModel { AboutViewModel() }

    override var about: About? by didSetNotNull { about ->
        frameworkNameView.text = about.frameworkName
        authorView.text = about.author
        Picasso.get().load(about.logoUrl).into(logoView)
    }

    override var isLoading: Boolean by didSet {
        loadingView.isVisible = isLoading
        contentView.isVisible = !isLoading
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<AboutFragment>(::AboutFragment)
}