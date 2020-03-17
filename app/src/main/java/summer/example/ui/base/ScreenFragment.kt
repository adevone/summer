package summer.example.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Router
import summer.SummerPresenter
import summer.android.SummerFragment
import summer.example.AppKodeinAware
import summer.example.presentation.base.ScreenPresenter
import summer.example.ui.ArgsFragmentFeature
import summer.example.ui.base.routing.BackButtonListener
import summer.example.ui.base.routing.RouterProvider

abstract class ScreenFragment<TArgs>(@LayoutRes layoutRes: Int) :
    SummerFragment(layoutRes),
    BackButtonListener,
    AppKodeinAware,
    ArgsFragmentFeature<TArgs> {

    override var argsBackingField: TArgs? = null

    @Suppress("LeakingThis")
    override val fragment: Fragment = this

    abstract val presenter: SummerPresenter<*>

    protected lateinit var ciceroneRouter: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ciceroneRouter = (parentFragment as RouterProvider).ciceroneRouter
    }

    override fun onBackPressed() = (presenter as? ScreenPresenter<*>)?.onBackClick() == true
}