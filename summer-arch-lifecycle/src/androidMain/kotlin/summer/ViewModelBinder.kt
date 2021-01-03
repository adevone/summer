package summer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

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
    private val getLifecycleViewModel: () -> LifecycleViewModel<TView>
) : LifecycleObserver, ViewModelBinder<TView> {

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

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startCreating() {
        isViewCreating = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun attachViewIfNeeded() {
        if (isViewCreating) {
            getLifecycleViewModel().getView = bindGetView
            getLifecycleViewModel().viewCreated()
            isViewCreating = false
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun detachView() {
        getLifecycleViewModel().getView = { null }
        isViewCreating = false
    }
}