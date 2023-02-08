package summer.android

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import summer.LifecycleViewModel
import summer.DidSetMixin

abstract class SummerFragment : BaseSummerFragment<SummerViewModelProvider<*, *>> {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    fun <TView, TViewModel : LifecycleViewModel<TView>> TView.bindViewModel(
        createViewModel: () -> TViewModel
    ): SummerViewModelProvider<TView, TViewModel> {
        val provider = SummerViewModelProvider(createViewModel, view = this)
        viewModelProvider = provider
        return provider
    }
}

abstract class BaseSummerFragment<TViewModelProvider : SummerViewModelProvider<*, *>> :
    PopListenerFragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider = requireViewModelProvider()
        provider.initViewModel()
    }

    private var isViewCreating = false

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isViewCreating = true
        super.onViewCreated(view, savedInstanceState)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        if (isViewCreating) {
            val provider = requireViewModelProvider()
            provider.viewCreated()
        }
        isViewCreating = false
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        viewModelProvider?.viewDestroyed()
    }

    override fun onResume() {
        super.onResume()
        viewModelProvider?.viewAppeared()
    }

    override fun onPause() {
        super.onPause()
        viewModelProvider?.viewDisappeared()
    }

    protected var viewModelProvider: TViewModelProvider? = null

    protected fun requireViewModelProvider(): TViewModelProvider {
        return viewModelProvider ?: throw ViewModelNotProvidedException()
    }

    companion object : DidSetMixin
}