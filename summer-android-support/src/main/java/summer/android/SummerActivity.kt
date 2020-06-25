package summer.android

import android.os.Bundle
import summer.LifecycleSummerPresenter
import android.support.v7.app.AppCompatActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        isCreating = true
        val presenterProvider = requirePresenterProvider()
        presenterProvider.initPresenter()
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (isCreating) {
            val presenterProvider = requirePresenterProvider()
            presenterProvider.viewCreated()
        }
        isCreating = false
    }

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