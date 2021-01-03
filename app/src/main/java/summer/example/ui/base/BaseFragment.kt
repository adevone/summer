package summer.example.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import ru.terrakok.cicerone.Router
import summer.DidSetMixin
import summer.example.AppKodeinAware
import summer.example.ViewModelFactory
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.ViewModelEventsListener
import summer.example.ui.ArgsFragmentFeature
import summer.example.ui.base.routing.BackButtonListener
import summer.example.ui.base.routing.RouterProvider
import kotlin.reflect.KClass

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
        ViewModelEventsListener.onDetach(viewModelClazz)
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

    lateinit var viewClazz: KClass<*>
    lateinit var viewModelClazz: KClass<*>
    inline fun <reified TView, reified TViewModel : BaseViewModel<TView>> bindViewModel(
        viewModelClass: KClass<TViewModel>,
        fragment: Fragment,
        noinline provideView: () -> TView
    ): TViewModel {
        val provider = ViewModelProvider(fragment, ViewModelFactory())
        val viewModel = provider[viewModelClass.java]
        viewModel.bindView(fragment.viewLifecycleOwner, provideView)
        viewClazz = TView::class
        viewModelClazz = TViewModel::class
        ViewModelEventsListener.onAttach(viewModelClazz, viewClazz)
        return viewModel
    }

    companion object : DidSetMixin
}

class ViewBindingNotProvidedException : IllegalStateException(
    "Please view binding: val binding by viewBinding { <BINDING>.inflate(it) }"
)