package summer.android

import summer.strategy.SerializationStateProvider
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import summer.LifecycleSummerPresenter

abstract class SaveStateSummerFragment : BaseSummerFragment<SaveStatePresenterProvider<*, *>> {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        requirePresenterProvider().onSaveInstanceState(outState)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requirePresenterProvider().onRestoreInstanceState(savedInstanceState)
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