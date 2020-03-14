package summer.example.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.about_fragment.*
import kotlinx.serialization.Serializable
import summer.example.R
import summer.example.entity.About
import summer.example.presentation.AboutPresenter
import summer.example.presentation.AboutRouter
import summer.example.presentation.AboutView
import summer.example.ui.base.ScreenFragment
import summer.example.ui.base.routing.ScreenArgs

class AboutFragment : ScreenFragment<
        AboutView.State,
        AboutView.Methods,
        AboutRouter,
        AboutPresenter,
        AboutFragment.Args>() {

    override val router = object : AboutRouter {}

    override fun createPresenter() = AboutPresenter()

    override fun createViewState() = object : AboutView.State {

        override var about: About? by didSetNotNull { about ->
            frameworkNameView.text = about.frameworkName
            authorView.text = about.author
            Picasso.get().load(about.logoUrl).into(logoView)
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

    override val viewMethods = object : AboutView.Methods {

    }

    override val screenToolbar get() = toolbar!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.about_fragment,
        container,
        false
    )!!

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<AboutFragment>()
}