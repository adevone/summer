package io.adev.summer.example.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.terrakok.cicerone.Router
import summer.DidSetMixin
import io.adev.summer.example.AppKodeinAware
import io.adev.summer.example.ui.ArgsFragmentFeature
import io.adev.summer.example.ui.base.routing.BackButtonListener
import io.adev.summer.example.ui.base.routing.RouterProvider

abstract class BaseFragment<TArgs> :
    Fragment(),
    BackButtonListener,
    AppKodeinAware,
    ArgsFragmentFeature<TArgs> {

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

    override var argsBackingField: TArgs? = null

    @Suppress("LeakingThis")
    override val fragment: Fragment = this

    protected lateinit var ciceroneRouter: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ciceroneRouter = (parentFragment as RouterProvider).ciceroneRouter
    }

    override fun onBackPressed(): Boolean = false

    companion object : DidSetMixin
}

class ViewBindingNotProvidedException : IllegalStateException(
    "Please view binding: val binding by viewBinding { <BINDING>.inflate(it) }"
)