package summer.android

import summer.SummerPresenter
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class PresenterProvider<TView, TPresenter : SummerPresenter<TView, *>>(
    private val createPresenter: () -> TPresenter,
    private val view: TView
) : ReadOnlyProperty<Any?, TPresenter> {

    private var presenter: TPresenter? = null

    fun initPresenter() {
        val presenter = createPresenter()
        presenter.getView = { view }
        this.presenter = presenter
    }

    fun viewCreated() {
        val presenter = requirePresenter()
        presenter.viewCreated()
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): TPresenter {
        return requirePresenter()
    }

    private fun requirePresenter(): TPresenter {
        return presenter ?: throw PresenterNotInitializedYet()
    }
}