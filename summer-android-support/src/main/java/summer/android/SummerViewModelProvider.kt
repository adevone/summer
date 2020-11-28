package summer.android

import summer.LifecycleSummerViewModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class SummerViewModelProvider<TView, out TViewModel : LifecycleSummerViewModel<TView>>(
    private val createViewModel: () -> TViewModel,
    private val view: TView
) : ReadOnlyProperty<Any?, TViewModel> {

    private var viewModel: TViewModel? = null

    fun initViewModel() {
        val viewModel = createViewModel()
        this.viewModel = viewModel
    }

    fun viewCreated() {
        val viewModel = requireViewModel()
        viewModel.getView = { view }
        viewModel.viewCreated()
    }

    fun viewDestroyed() {
        viewModel?.getView = { null }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): TViewModel {
        return requireViewModel()
    }

    fun requireViewModel(): TViewModel {
        return viewModel ?: throw ViewModelNotInitializedYet()
    }
}

class ViewModelNotInitializedYet : IllegalStateException("viewModel not initialized yet")