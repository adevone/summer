package summer.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import summer.LifecycleViewModel

abstract class SummerActivity : BaseSummerActivity<SummerViewModelProvider<*, *>>() {

    fun <TView, TViewModel : LifecycleViewModel<TView>> TView.bindViewModel(
        createViewModel: () -> TViewModel
    ): SummerViewModelProvider<TView, TViewModel> {
        val provider = SummerViewModelProvider(createViewModel, view = this)
        viewModelProvider = provider
        return provider
    }
}

abstract class BaseSummerActivity<TViewModelProvider : SummerViewModelProvider<*, *>> :
    AppCompatActivity() {

    private var isCreating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        isCreating = true
        val viewModelProvider = requireViewModelProvider()
        viewModelProvider.initViewModel()
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (isCreating) {
            val viewModelProvider = requireViewModelProvider()
            viewModelProvider.viewCreated()
        }
        isCreating = false
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelProvider?.viewDestroyed()
    }

    protected var viewModelProvider: TViewModelProvider? = null

    protected fun requireViewModelProvider(): TViewModelProvider {
        return viewModelProvider ?: throw ViewModelNotProvidedException()
    }

    companion object : DidSetMixin
}