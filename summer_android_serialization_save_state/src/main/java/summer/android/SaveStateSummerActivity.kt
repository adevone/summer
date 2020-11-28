package summer.android

import android.os.Bundle
import summer.LifecycleSummerViewModel
import summer.strategy.SerializationStateProvider

abstract class SaveStateSummerActivity : BaseSummerActivity<SaveStateViewModelProvider<*, *>>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireViewModelProvider().onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        requireViewModelProvider().onSaveInstanceState(outState)
    }

    fun <TView, TViewModel> TView.bindViewModel(
        createViewModel: () -> TViewModel
    ): SaveStateViewModelProvider<TView, TViewModel>
        where TViewModel : LifecycleSummerViewModel<TView>, TViewModel : SerializationStateProvider {
        val provider = SaveStateViewModelProvider(createViewModel, view = this)
        viewModelProvider = provider
        return provider
    }

}