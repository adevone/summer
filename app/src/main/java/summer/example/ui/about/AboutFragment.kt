package summer.example.ui.about

import android.os.Bundle
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import kotlinx.serialization.Serializable
import summer.example.databinding.AboutFragmentBinding
import summer.example.entity.About
import summer.example.presentation.AboutView
import summer.example.presentation.AboutViewModel
import summer.example.ui.base.BaseFragment
import summer.example.ui.base.routing.ScreenArgs

class AboutFragment : BaseFragment<AboutFragment.Args>(), AboutView {
    private val binding by viewBinding { AboutFragmentBinding.inflate(it) }

    private lateinit var viewModel: AboutViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = bindViewModel(AboutViewModel::class, fragment = this) { this }
    }

    override var about: About? by didSetNotNull { about ->
        binding.frameworkNameView.text = about.frameworkName
        binding.authorView.text = about.author
        Picasso.get().load(about.logoUrl).into(binding.logoView)
    }

    override var isLoading: Boolean by didSet {
        binding.loadingView.isVisible = isLoading
        binding.contentView.isVisible = !isLoading
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args : ScreenArgs<AboutFragment>(::AboutFragment)
}