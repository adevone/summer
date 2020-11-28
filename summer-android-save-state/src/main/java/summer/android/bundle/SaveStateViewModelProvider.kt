package summer.android.bundle

import android.os.Bundle
import summer.android.SummerViewModelProvider

class SaveStateViewModelProvider<TView, out TViewModel : SaveStateSummerViewModel<TView>>(
    createViewModel: () -> TViewModel, view: TView
) : SummerViewModelProvider<TView, TViewModel>(
    createViewModel, view
) {
    fun onSaveInstanceState(outState: Bundle) {
        val viewModel = requireViewModel()
        outState.putAll(viewModel.bundle)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        val viewModel = requireViewModel()
        if (savedInstanceState != null) {
            viewModel.bundle = savedInstanceState
        } else {
            viewModel.bundle = Bundle()
        }
    }
}