package summer.android

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import summer.LifecycleSummerPresenter

abstract class SummerActivity : BaseSummerActivity<PresenterProvider<*, *>>() {

    fun <TView, TPresenter : LifecycleSummerPresenter<TView>> TView.bindPresenter(
        createPresenter: () -> TPresenter
    ): PresenterProvider<TView, TPresenter> {
        val provider = PresenterProvider(createPresenter, view = this)
        presenterProvider = provider
        return provider
    }
}

abstract class BaseSummerActivity<TPresenterProvider : PresenterProvider<*, *>> :
    AppCompatActivity() {

    private var isCreating = false

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        isCreating = true
        val presenterProvider = requirePresenterProvider()
        presenterProvider.initPresenter()
        super.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        if (isCreating) {
            val presenterProvider = requirePresenterProvider()
            presenterProvider.viewCreated()
        }
        isCreating = false
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        presenterProvider?.viewDestroyed()
    }

    protected var presenterProvider: TPresenterProvider? = null

    protected fun requirePresenterProvider(): TPresenterProvider {
        return presenterProvider ?: throw PresenterNotProvidedException()
    }

    companion object : DidSetMixin
}