package summer.android

import android.os.Bundle
import summer.LifecycleSummerPresenter
import summer.strategy.SerializationStateProvider

abstract class SaveStateSummerActivity : BaseSummerActivity<SaveStatePresenterProvider<*, *>>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requirePresenterProvider().onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        requirePresenterProvider().onSaveInstanceState(outState)
    }

    fun <TView, TPresenter> TView.bindPresenter(
        createPresenter: () -> TPresenter
    ): SaveStatePresenterProvider<TView, TPresenter>
        where TPresenter : LifecycleSummerPresenter<TView>, TPresenter : SerializationStateProvider {
        val provider = SaveStatePresenterProvider(createPresenter, view = this)
        presenterProvider = provider
        return provider
    }

}