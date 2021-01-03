package io.adev.summer.example.ui.about

import android.os.Bundle
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import io.adev.summer.example.bindViewModel
import io.adev.summer.example.databinding.AboutFragmentBinding
import io.adev.summer.example.entity.About
import io.adev.summer.example.presentation.AboutView
import io.adev.summer.example.presentation.AboutViewModel
import io.adev.summer.example.ui.base.BaseFragment
import io.adev.summer.example.ui.base.routing.ScreenArgs
import kotlinx.serialization.Serializable

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