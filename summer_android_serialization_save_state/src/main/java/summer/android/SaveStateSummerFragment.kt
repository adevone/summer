package summer.android

import summer.strategy.SerializationStateProvider
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import summer.LifecycleSummerPresenter

abstract class SaveStateSummerFragment : PopListenerFragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val provider = requirePresenterProvider()
        provider.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider = requirePresenterProvider()
        provider.initPresenter()
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
            val provider = requirePresenterProvider()
            provider.viewCreated()
        }
        isViewCreating = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenterProvider?.viewDestroyed()
    }

    private var presenterProvider: SaveStatePresenterProvider<*, *>? = null
    fun <TView, TPresenter> TView.bindPresenter(
        createPresenter: () -> TPresenter
    ): SaveStatePresenterProvider<TView, TPresenter>
        where TPresenter : LifecycleSummerPresenter<TView>, TPresenter : SerializationStateProvider {
        val provider = SaveStatePresenterProvider(createPresenter, view = this)
        presenterProvider = provider
        return provider
    }

    private fun requirePresenterProvider(): SaveStatePresenterProvider<*, *> {
        return presenterProvider ?: throw PresenterNotProvidedException()
    }

    companion object : DidSetMixin
}