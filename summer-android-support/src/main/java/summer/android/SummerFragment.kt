package summer.android

import android.os.Bundle
import android.view.View
import summer.LifecycleViewModel
import summer.DidSetMixin

abstract class SummerFragment : BaseSummerFragment<SummerViewModelProvider<*, *>>() {

    fun <TView, TViewModel : LifecycleViewModel<TView>> TView.bindViewModel(
        createViewModel: () -> TViewModel
    ): SummerViewModelProvider<TView, TViewModel> {
        val provider = SummerViewModelProvider(createViewModel, view = this)
        viewModelProvider = provider
        return provider
    }
}

abstract class BaseSummerFragment<TViewModelProvider : SummerViewModelProvider<*, *>> :
    PopListenerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider = requireViewModelProvider()
        provider.initViewModel()
    }

    private var isViewCreating = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isViewCreating = true
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (isViewCreating) {
            val provider = requireViewModelProvider()
            provider.viewCreated()
        }
        isViewCreating = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelProvider?.viewDestroyed()
    }

    protected var viewModelProvider: TViewModelProvider? = null

    protected fun requireViewModelProvider(): TViewModelProvider {
        return viewModelProvider ?: throw ViewModelNotProvidedException()
    }

    companion object : DidSetMixin
}