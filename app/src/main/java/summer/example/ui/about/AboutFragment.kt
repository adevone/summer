package summer.example.ui.about

import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.about_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.About
import summer.example.presentation.AboutPresenter
import summer.example.presentation.AboutView
import summer.example.ui.base.ScreenFragment
import summer.example.ui.base.routing.ScreenArgs

class AboutFragment : ScreenFragment<
        AboutView,
        AboutPresenter,
        AboutFragment.Args>(R.layout.about_fragment) {

    override fun createPresenter() = AboutPresenter()

    override fun createViewState() = object : AboutView {

        override var about: About? by didSetNotNull { about ->
            frameworkNameView.text = about.frameworkName
            authorView.text = about.author
            Picasso.get().load(about.logoUrl).into(logoView)
        }

        override val doSomething = { smth: String ->
            println(smth)
        }

        override var isLoading: Boolean by didSet {
            if (isLoading) {
                loadingView.visibility = View.VISIBLE
                contentView.visibility = View.GONE
            } else {
                loadingView.visibility = View.GONE
                contentView.visibility = View.VISIBLE
            }
        }
    }

    override val screenToolbar get() = toolbar!!

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<AboutFragment>()
}