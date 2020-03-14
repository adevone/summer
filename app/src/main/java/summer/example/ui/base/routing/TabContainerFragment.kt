package summer.example.ui.base.routing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import kotlinx.serialization.Serializable
import org.kodein.di.erased.instance
import summer.example.AppKodeinAware
import summer.example.ui.about.AboutFragment
import summer.example.ui.frameworks.FrameworksFragment
import summer.example.ui.shoppingCart.ShoppingCartFragment
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import summer.example.entity.Tab
import summer.example.ui.ArgsFragment

class TabContainerFragment : ArgsFragment<TabContainerFragment.Args>(), AppKodeinAware, RouterProvider, BackButtonListener {

    private var navigator: Navigator? = null

    private val ciceroneHolder: LocalCiceroneHolder by instance()
    private val cicerone: Cicerone<Router> get() = ciceroneHolder.getCicerone(args.item)

    override val ciceroneRouter: Router get() = cicerone.router

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FrameLayout(container!!.context).apply {
        id = tabContainerId
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(tabContainerId) == null) {

            val screen = when (args.item) {
                Tab.Frameworks -> FrameworksFragment.Args().toScreen()
                Tab.About -> AboutFragment.Args().toScreen()
                Tab.Basket -> ShoppingCartFragment.Args().toScreen()
            }

            cicerone.router.replaceScreen(screen)
        }
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun getNavigator(): Navigator {
        if (navigator == null) {
            navigator = SupportAppNavigator(requireActivity(), childFragmentManager, tabContainerId)
        }
        return navigator!!
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(tabContainerId)
        val isHandled = fragment is BackButtonListener && (fragment as BackButtonListener).onBackPressed()
        return if (isHandled) {
            true
        } else {
            if (childFragmentManager.backStackEntryCount != 0) {
                ciceroneRouter.exit()
                true
            } else {
                false
            }
        }
    }

    override val argsSerializer = Args.serializer()

    @Serializable
    class Args(
        val item: Tab
    ) : ScreenArgs<TabContainerFragment>()

    companion object {
        private val tabContainerId = ViewCompat.generateViewId()
    }
}
