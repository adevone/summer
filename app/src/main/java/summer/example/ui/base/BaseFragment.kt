package summer.example.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Router
import summer.LifecycleSummerViewModel
import summer.android.BaseSummerFragment
import summer.android.SummerFragment
import summer.android.SummerViewModelProvider
import summer.example.AppKodeinAware
import summer.example.presentation.BaseSaveStateViewModel
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.ViewModelEventsListener
import summer.example.ui.ArgsFragmentFeature
import summer.example.ui.base.routing.BackButtonListener
import summer.example.ui.base.routing.RouterProvider
import kotlin.reflect.KClass

abstract class BaseFragment<TArgs>(@LayoutRes layoutRes: Int) :
    BaseSummerFragment<SummerViewModelProvider<*, *>>(layoutRes),
    BackButtonListener,
    AppKodeinAware,
    ArgsFragmentFeature<TArgs> {

    lateinit var viewClazz: KClass<*>
    lateinit var viewModelClazz: KClass<*>

    inline fun <reified TView, reified TViewModel : LifecycleSummerViewModel<TView>> TView.bindViewModel(
        noinline createViewModel: () -> TViewModel
    ): SummerViewModelProvider<TView, TViewModel> {
        val provider = SummerViewModelProvider(createViewModel, view = this)
        assignViewModelProvider(provider)
        viewClazz = TView::class
        viewModelClazz = TViewModel::class
        return provider
    }

    fun assignViewModelProvider(provider: SummerViewModelProvider<*, *>) {
        viewModelProvider = provider
    }

    override var argsBackingField: TArgs? = null

    @Suppress("LeakingThis")
    override val fragment: Fragment = this

    abstract val viewModel: BaseViewModel<*>

    protected lateinit var ciceroneRouter: Router

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewModelEventsListener.onAttach(viewModelClazz, viewClazz)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ViewModelEventsListener.onDetach(viewModelClazz)
    }

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

abstract class BaseSaveInstanceStateFragment<TArgs>(@LayoutRes layoutRes: Int) :
    SummerFragment(layoutRes),
    BackButtonListener,
    AppKodeinAware,
    ArgsFragmentFeature<TArgs> {

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