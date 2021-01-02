package summer.example.ui.about

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
    override val viewModel by bindViewModel { AboutViewModel() }
    private val binding by viewBinding { AboutFragmentBinding.inflate(it) }

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