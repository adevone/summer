package summer.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import summer.SummerPresenter

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
            presenterProvider.created()
            presenterProvider.viewCreated()
            presenterProvider.entered()
        }
        isCreating = false
    }

    override fun onDestroy() {
        super.onDestroy()
        val presenterProvider = requirePresenterProvider()
        presenterProvider.viewDestroyed()
        presenterProvider.destroyed()
    }

    override fun onResume() {
        super.onResume()
        val presenterProvider = requirePresenterProvider()
        presenterProvider.appeared()
    }

    override fun onPause() {
        super.onPause()
        val presenterProvider = requirePresenterProvider()
        presenterProvider.disappeared()
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