package summer.android

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import summer.SummerPresenter
import java.util.Collections.emptyList

abstract class SummerFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected open fun createComponents(): List<SummerComponent<*, *>> = emptyList()
    private var lifecycleComponents: List<SummerComponent<*, *>> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider = requirePresenterProvider()
        provider.initPresenter()
        lifecycleComponents = createComponents()
        lifecycleComponents.forEach { it.onCreate() }
        provider.created()
    }

    private var isFirstViewCreation = true
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
            lifecycleComponents.forEach { component ->
                component.onViewCreated(
                    parentView = view as? ViewGroup,
                    context = context!!,
                    isFirstViewCreation = isFirstViewCreation
                )
            }
            provider.viewCreated()
            if (isFirstViewCreation) {
                provider.entered()
                isFirstViewCreation = false
            }
        }
        isViewCreating = false
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        val presenterProvider = requirePresenterProvider()
        presenterProvider.viewDestroyed()
        lifecycleComponents.forEach { it.onDestroyView() }
    }

    @CallSuper
    override fun onDestroy() {
        notifyPresenterIfRemoving()
        super.onDestroy()
        val presenterProvider = requirePresenterProvider()
        presenterProvider.destroyed()
        lifecycleComponents.forEach { it.onDestroy() }
        lifecycleComponents = emptyList()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        val presenterProvider = requirePresenterProvider()
        presenterProvider.appeared()
        lifecycleComponents.forEach { it.onResume() }
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        lifecycleComponents.forEach { it.onPause() }
        val presenterProvider = requirePresenterProvider()
        presenterProvider.disappeared()
    }

    private fun notifyPresenterIfRemoving() {
        var anyParentIsRemoving = false

        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            val presenterProvider = requirePresenterProvider()
            presenterProvider.exited()
            lifecycleComponents.forEach { it.onExit() }
        }
    }

    private var presenterProvider: PresenterProvider<*, *>? = null
    fun <TView, TPresenter : SummerPresenter<TView>> TView.summerPresenter(
        createPresenter: () -> TPresenter
    ): PresenterProvider<TView, TPresenter> {
        val provider = PresenterProvider(createPresenter, view = this)
        presenterProvider = provider
        return provider
    }

    private fun requirePresenterProvider(): PresenterProvider<*, *> {
        return presenterProvider ?: throw PresenterNotProvidedException()
    }

    companion object : DidSetMixin()
}

class PresenterNotProvidedException : RuntimeException(
    "presenter in not provided"
)

class PresenterNotInitializedYet() : RuntimeException(
    "presenter not initialized yet"
)