package summer.example.ui.about

import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.about_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.About
import summer.example.presentation.AboutPresenter
import summer.example.presentation.AboutView
import summer.example.ui.base.ScreenFragment
import summer.example.ui.base.routing.ScreenArgs

class AboutFragment : ScreenFragment<AboutFragment.Args>(R.layout.about_fragment), AboutView {

    override val presenter by summerPresenter { AboutPresenter() }

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