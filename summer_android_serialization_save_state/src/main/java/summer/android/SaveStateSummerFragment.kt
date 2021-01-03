package summer.android

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import summer.LifecycleViewModel
import summer.strategy.SerializationStore

abstract class SaveStateSummerFragment : BaseSummerFragment<SaveStateViewModelProvider<*, *>> {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        requireViewModelProvider().onSaveInstanceState(outState)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireViewModelProvider().onRestoreInstanceState(savedInstanceState)
    }

    fun <TView, TViewModel> TView.bindViewModel(
        createViewModel: () -> TViewModel,
    ): SaveStateViewModelProvider<TView, TViewModel>
            where TViewModel : LifecycleViewModel<TView>,
                  TViewModel : SerializationStore.Holder {
        val provider = SaveStateViewModelProvider(createViewModel, view = this)
        viewModelProvider = provider
        return provider
    }
}