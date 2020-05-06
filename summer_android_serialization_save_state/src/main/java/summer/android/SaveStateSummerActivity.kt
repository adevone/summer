package summer.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import summer.LifecycleSummerPresenter
import summer.strategy.SerializationStateProvider

abstract class SaveStateSummerActivity : AppCompatActivity() {

    private var isCreating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        isCreating = true
        val presenterProvider = requirePresenterProvider()
        presenterProvider.initPresenter()
        presenterProvider.onRestoreInstanceState(savedInstanceState)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val provider = requirePresenterProvider()
        provider.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterProvider?.viewDestroyed()
    }

    private var presenterProvider: SaveStatePresenterProvider<*, *>? = null
    fun <TView, TPresenter> TView.bindPresenter(
        createPresenter: () -> TPresenter
    ): SaveStatePresenterProvider<TView, TPresenter>
        where TPresenter : LifecycleSummerPresenter<TView>, TPresenter : SerializationStateProvider {
        val provider = SaveStatePresenterProvider(createPresenter, view = this)
        presenterProvider = provider
        return provider
    }

    private fun requirePresenterProvider(): SaveStatePresenterProvider<*, *> {
        return presenterProvider ?: throw PresenterNotProvidedException()
    }

    companion object : DidSetMixin
}