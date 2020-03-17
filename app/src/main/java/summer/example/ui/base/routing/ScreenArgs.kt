package summer.example.ui.base.routing

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import summer.example.ui.ArgsFragmentFeature

@Suppress("unused") // TFragment нужен для вывода типов в toScreen
abstract class ScreenArgs<TFragment : ArgsFragmentFeature<*>>(
    val createFragment: () -> TFragment
) {
    @Deprecated("use primary constructor", level = DeprecationLevel.ERROR)
    constructor() : this({ throw IllegalArgumentException("use primary constructor") })
}

class Screen<TFragment, TArgs : ScreenArgs<TFragment>>(
    private val args: TArgs,
    private val createFragment: () -> TFragment
) : SupportAppScreen() where TFragment : ArgsFragmentFeature<*>, TFragment : Fragment {
    override fun getFragment(): Fragment = (createFragment() as Fragment)
        .also {
            @Suppress("UNCHECKED_CAST")
            (it as ArgsFragmentFeature<TArgs>).args = args
        }
}

inline fun <
        reified TFragment,
        TArgs : ScreenArgs<TFragment>> TArgs.toScreen(): Screen<TFragment, TArgs>
        where TFragment : ArgsFragmentFeature<*>, TFragment : Fragment {
    return Screen(args = this@toScreen, createFragment = this.createFragment)
}