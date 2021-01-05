package io.adev.summer.example.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.adev.summer.example.AppKodeinAware
import io.adev.summer.example.AppNavigator
import io.adev.summer.example.presentation.base.NavigationView
import io.adev.summer.example.ui.MainActivity
import summer.DidSetMixin

abstract class BaseFragment : Fragment(), NavigationView, AppKodeinAware {

    private var viewBindingDelegate: ViewBindingDelegate<*>? = null
    fun <TBinding : ViewBinding> viewBinding(
        createBinding: (LayoutInflater) -> TBinding
    ): ViewBindingDelegate<TBinding> {
        return ViewBindingDelegate(createBinding).also {
            viewBindingDelegate = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val delegate = viewBindingDelegate ?: throw ViewBindingNotProvidedException()
        return delegate.inflateBinding(inflater).root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBindingDelegate?.clearBinding()
    }

    companion object : DidSetMixin

    override val navigate: (navigation: (AppNavigator) -> Unit) -> Unit = { navigate ->
        val navigator = (requireActivity() as MainActivity).navigator
        navigate(navigator)
    }
}

class ViewBindingNotProvidedException : IllegalStateException(
    "Please provide view binding: val binding by viewBinding { <BINDING>.inflate(it) }"
)