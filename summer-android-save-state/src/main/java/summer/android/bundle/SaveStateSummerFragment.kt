package summer.android.bundle

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import summer.android.DidSetMixin
import summer.android.PopListenerFragment
import summer.android.ViewModelNotProvidedException

abstract class SaveStateSummerFragment : PopListenerFragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val provider = requireViewModelProvider()
        provider.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider = requireViewModelProvider()
        provider.initViewModel()
        provider.onRestoreInstanceState(savedInstanceState)
    }

    private var isViewCreating = false

    @CallSuper
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

    private var viewModelProvider: SaveStateViewModelProvider<*, *>? = null
    fun <TView, TViewModel : SaveStateSummerViewModel<TView>> TView.bindViewModel(
        createViewModel: () -> TViewModel
    ): SaveStateViewModelProvider<TView, TViewModel> {
        val provider = SaveStateViewModelProvider(createViewModel, view = this)
        viewModelProvider = provider
        return provider
    }

    private fun requireViewModelProvider(): SaveStateViewModelProvider<*, *> {
        return viewModelProvider ?: throw ViewModelNotProvidedException()
    }

    companion object : DidSetMixin
}