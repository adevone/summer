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
            presenterProvider.viewCreated()
        }
        isCreating = false
    }

    private var presenterProvider: PresenterProvider<*, *>? = null
    fun <TView, TPresenter : SummerPresenter<TView, *>> TView.summerPresenter(
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