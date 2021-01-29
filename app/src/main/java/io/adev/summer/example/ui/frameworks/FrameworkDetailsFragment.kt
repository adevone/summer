package io.adev.summer.example.ui.frameworks

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import io.adev.summer.example.bindViewModel
import io.adev.summer.example.databinding.FrameworkDetailsFragmentBinding
import io.adev.summer.example.entity.Framework
import io.adev.summer.example.entity.FullFramework
import io.adev.summer.example.presentation.FrameworkDetailsView
import io.adev.summer.example.presentation.FrameworkDetailsViewModel
import io.adev.summer.example.ui.base.BaseFragment
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FrameworkDetailsFragment : BaseFragment(), FrameworkDetailsView {

    private val binding by viewBinding { FrameworkDetailsFragmentBinding.inflate(it) }

    private lateinit var viewModel: FrameworkDetailsViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = bindViewModel(FrameworkDetailsViewModel::class, fragment = this) { this }

        val frameworkString = arguments?.getString(FRAMEWORK_KEY) ?: return
        val framework = Json.decodeFromString(Framework.serializer(), frameworkString)
        viewModel.init(initialFramework = framework)
    }

    override var framework: FullFramework? by didSet {
        binding.nameView.text = framework?.name ?: ""
        binding.versionView.text = framework?.version ?: ""
    }

    override var notifyAboutName = { frameworkName: String ->
        Toast.makeText(context, frameworkName, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val FRAMEWORK_KEY = "framework_key"

        fun newInstance(framework: Framework) = FrameworkDetailsFragment().also {
            it.arguments = bundleOf(
                FRAMEWORK_KEY to Json.encodeToString(framework)
            )
        }
    }
}