package summer.android

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import summer.BaseSummerPresenter

abstract class SummerFragment : PopListenerFragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider = requirePresenterProvider()
        provider.initPresenter()
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

    private var presenterProvider: PresenterProvider<*, *>? = null
    fun <TView, TPresenter : BaseSummerPresenter<TView>> TView.bindPresenter(
        createPresenter: () -> TPresenter
    ): PresenterProvider<TView, TPresenter> {
        val provider = PresenterProvider(createPresenter, view = this)
        presenterProvider = provider
        return provider
    }

    private fun requirePresenterProvider(): PresenterProvider<*, *> {
        return presenterProvider ?: throw PresenterNotProvidedException()
    }

    companion object : DidSetMixin
}

abstract class PopListenerFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    @CallSuper
    open fun onPop() {
    }

    @CallSuper
    override fun onDestroy() {
        notifyPresenterIfRemoving()
        super.onDestroy()
    }

    private fun notifyPresenterIfRemoving() {
        var anyParentIsRemoving = false

        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            onPop()
        }
    }
}

class PresenterNotProvidedException : IllegalStateException("presenter in not provided")