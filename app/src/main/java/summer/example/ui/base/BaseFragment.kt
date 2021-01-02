package summer.example.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.terrakok.cicerone.Router
import summer.android.SummerFragment
import summer.example.AppKodeinAware
import summer.example.presentation.BaseSaveStateViewModel
import summer.example.presentation.base.BaseViewModel
import summer.example.ui.ArgsFragmentFeature
import summer.example.ui.base.routing.BackButtonListener
import summer.example.ui.base.routing.RouterProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseFragment<TArgs> :
    SummerFragment(),
    BackButtonListener,
    AppKodeinAware,
    ArgsFragmentFeature<TArgs> {

    private var createBinding: ((LayoutInflater) -> ViewBinding)? = null
    protected fun <TBinding : ViewBinding> viewBinding(
        createBinding: (LayoutInflater) -> TBinding
    ): ViewBindingDelegate<TBinding> {
        this.createBinding = createBinding
        return ViewBindingDelegate()
    }

    private var _binding: ViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = (createBinding ?: throw ViewBindingNotProvidedException())(inflater)
        return _binding!!.root
    }

    protected inner class ViewBindingDelegate<TBinding : ViewBinding>
        : ReadOnlyProperty<Any?, TBinding> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): TBinding {
            @Suppress("UNCHECKED_CAST")
            return _binding!! as TBinding
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override var argsBackingField: TArgs? = null

    @Suppress("LeakingThis")
    override val fragment: Fragment = this

    abstract val viewModel: BaseViewModel<*>

    protected lateinit var ciceroneRouter: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ciceroneRouter = (parentFragment as RouterProvider).ciceroneRouter
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    override fun onBackPressed(): Boolean = viewModel.onBackClick()
}

class ViewBindingNotProvidedException : IllegalStateException()

abstract class BaseSaveInstanceStateFragment<TArgs> :
    SummerFragment(),
    BackButtonListener,
    AppKodeinAware,
    ArgsFragmentFeature<TArgs> {

    private var createBinding: ((LayoutInflater) -> ViewBinding)? = null
    protected fun <TBinding : ViewBinding> viewBinding(
        createBinding: (LayoutInflater) -> TBinding
    ): ViewBindingDelegate<TBinding> {
        this.createBinding = createBinding
        return ViewBindingDelegate()
    }

    private var _binding: ViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = (createBinding ?: throw ViewBindingNotProvidedException())(inflater)
        return _binding!!.root
    }

    protected inner class ViewBindingDelegate<TBinding : ViewBinding>
        : ReadOnlyProperty<Any?, TBinding> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): TBinding {
            @Suppress("UNCHECKED_CAST")
            return _binding!! as TBinding
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override var argsBackingField: TArgs? = null

    @Suppress("LeakingThis")
    override val fragment: Fragment = this

    abstract val viewModel: BaseSaveStateViewModel<*>

    protected lateinit var ciceroneRouter: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ciceroneRouter = (parentFragment as RouterProvider).ciceroneRouter
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    override fun onBackPressed(): Boolean = viewModel.onBackClick()
}