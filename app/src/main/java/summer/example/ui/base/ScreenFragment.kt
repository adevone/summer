package summer.example.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Router
import summer.SummerPresenterWithRouter
import summer.android.SummerFragmentWithRouter
import summer.example.AppKodeinAware
import summer.example.presentation.base.ScreenPresenter
import summer.example.ui.ArgsFragmentFeature
import summer.example.ui.base.routing.BackButtonListener
import summer.example.ui.base.routing.RouterProvider

abstract class ScreenFragment<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any,
        TPresenter : SummerPresenterWithRouter<TViewState, TViewMethods, TRouter>,
        TArgs> :
    SummerFragmentWithRouter<
            TViewState,
            TViewMethods,
            TRouter,
            TPresenter>(),
    BackButtonListener,
    AppKodeinAware,
    ArgsFragmentFeature<TArgs> {

    override var argsBackingField: TArgs? = null
    override val fragment: Fragment = this

    protected lateinit var ciceroneRouter: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ciceroneRouter = (parentFragment as RouterProvider).ciceroneRouter
    }

    protected abstract val screenToolbar: Toolbar?

    override fun initView() {
        val appCompatActivity = activity as AppCompatActivity
        if (screenToolbar != null) {
            appCompatActivity.setSupportActionBar(screenToolbar!!)
        }
    }

    override fun onBackPressed() = (presenter as? ScreenPresenter<*, *, *>)?.onBackClick() == true
}