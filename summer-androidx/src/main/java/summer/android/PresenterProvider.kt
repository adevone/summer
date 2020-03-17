package summer.android

import summer.SummerPresenter
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class PresenterProvider<TView, TPresenter : SummerPresenter<TView>>(
    private val createPresenter: () -> TPresenter,
    private val view: TView
) : ReadOnlyProperty<Any?, TPresenter> {

    private var presenter: TPresenter? = null

    fun initPresenter() {
        presenter = createPresenter()
    }

    fun created() {
        val presenter = requirePresenter()
        presenter.created()
    }

    fun destroyed() {
        val presenter = requirePresenter()
        presenter.destroyed()
    }

    fun viewCreated() {
        val presenter = requirePresenter()
        presenter.viewCreated(view)
    }

    fun viewDestroyed() {
        val presenter = requirePresenter()
        presenter.viewDestroyed()
    }

    fun entered() {
        val presenter = requirePresenter()
        presenter.entered()
    }

    fun exited() {
        val presenter = requirePresenter()
        presenter.exited()
    }

    fun appeared() {
        val presenter = requirePresenter()
        presenter.appeared()
    }

    fun disappeared() {
        val presenter = requirePresenter()
        presenter.disappeared()
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): TPresenter {
        return requirePresenter()
    }

    private fun requirePresenter(): TPresenter {
        return presenter ?: throw PresenterNotInitializedYet()
    }
}