package summer.android

import summer.LifecycleSummerPresenter
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class PresenterProvider<TView, out TPresenter : LifecycleSummerPresenter<TView>>(
    private val createPresenter: () -> TPresenter,
    private val view: TView
) : ReadOnlyProperty<Any?, TPresenter> {

    private var presenter: TPresenter? = null

    fun initPresenter() {
        val presenter = createPresenter()
        this.presenter = presenter
    }

    fun viewCreated() {
        val presenter = requirePresenter()
        presenter.getView = { view }
        presenter.viewCreated()
    }

    fun viewDestroyed() {
        presenter?.getView = { null }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): TPresenter {
        return requirePresenter()
    }

    fun requirePresenter(): TPresenter {
        return presenter ?: throw PresenterNotInitializedYet()
    }
}

class PresenterNotInitializedYet : IllegalStateException("presenter not initialized yet")