package summer.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import summer.BaseSummerPresenter

abstract class SummerActivity : AppCompatActivity() {

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