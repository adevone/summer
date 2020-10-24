package summer.android

import android.os.Bundle
import android.view.View
import summer.LifecycleSummerPresenter
import android.support.v4.app.Fragment

abstract class SummerFragment : BaseSummerFragment<PresenterProvider<*, *>>() {

    fun <TView, TPresenter : LifecycleSummerPresenter<TView>> TView.bindPresenter(
        createPresenter: () -> TPresenter
    ): PresenterProvider<TView, TPresenter> {
        val provider = PresenterProvider(createPresenter, view = this)
        presenterProvider = provider
        return provider
    }
}

abstract class BaseSummerFragment<TPresenterProvider : PresenterProvider<*, *>> :
    PopListenerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider = requirePresenterProvider()
        provider.initPresenter()
    }

    private var isViewCreating = false

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

    protected var presenterProvider: TPresenterProvider? = null

    protected fun requirePresenterProvider(): TPresenterProvider {
        return presenterProvider ?: throw PresenterNotProvidedException()
    }

    companion object : DidSetMixin
}