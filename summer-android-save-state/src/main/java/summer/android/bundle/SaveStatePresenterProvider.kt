package summer.android.bundle

import android.os.Bundle
import summer.android.PresenterProvider

class SaveStatePresenterProvider<TView, out TPresenter : SaveStateSummerPresenter<TView>>(
    createPresenter: () -> TPresenter, view: TView
) : PresenterProvider<TView, TPresenter>(
    createPresenter, view
) {
    fun onSaveInstanceState(outState: Bundle) {
        val presenter = requirePresenter()
        outState.putAll(presenter.bundle)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        val presenter = requirePresenter()
        if (savedInstanceState != null) {
            presenter.bundle = savedInstanceState
        } else {
            presenter.bundle = Bundle()
        }
    }
}