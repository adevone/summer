package summer.arch

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import summer.DefaultSummerViewModel
import summer.DefaultSummerViewModelImpl
import summer.LifecycleViewModel

interface ViewModelBinder<TView> {
    fun bindView(owner: LifecycleOwner, provideView: () -> TView?)
}

interface BindViewModel<TView> : ViewModelBinder<TView>, DefaultSummerViewModel<TView>

open class BindViewModelImpl<TView> :
    BindViewModel<TView>,
    DefaultSummerViewModelImpl<TView>() {

    private val binder = SummerViewBinder(
        getLifecycleViewModel = { this }
    )

    override fun bindView(owner: LifecycleOwner, provideView: () -> TView?) {
        binder.bindView(owner, provideView)
    }
}

class SummerViewBinder<TView>(
    private val getLifecycleViewModel: () -> LifecycleViewModel<TView>,
) : LifecycleEventObserver, ViewModelBinder<TView> {

    private var bindGetView: () -> TView? = { null }
    override fun bindView(owner: LifecycleOwner, provideView: () -> TView?) {

        // Not just go [provideView] because [provideView] must return null
        // when real view is not present
        bindGetView = provideView

        // If [bind] called from Fragment.onActivityCreated than
        // it could be called multiple times on the same fragment
        owner.lifecycle.removeObserver(this)

        owner.lifecycle.addObserver(this)
    }

    private var isViewCreating = false

    private fun startCreating() {
        isViewCreating = true
    }

    private fun attachViewIfNeeded() {
        if (isViewCreating) {
            getLifecycleViewModel().getView = bindGetView
            getLifecycleViewModel().viewCreated()
            isViewCreating = false
        }
    }

    private fun detachView() {
        getLifecycleViewModel().getView = { null }
        isViewCreating = false
    }

    private fun viewAppeared() {
        getLifecycleViewModel().viewAppeared()
    }

    private fun viewDisappeared() {
        getLifecycleViewModel().viewDisappeared()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> startCreating()
            Lifecycle.Event.ON_START -> attachViewIfNeeded()
            Lifecycle.Event.ON_RESUME -> viewAppeared()
            Lifecycle.Event.ON_PAUSE -> viewDisappeared()
            Lifecycle.Event.ON_DESTROY -> detachView()
            else -> Unit
        }
    }
}
