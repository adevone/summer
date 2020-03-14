package summer.example.ui.base.routing

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import summer.example.ui.ArgsFragmentFeature
import kotlin.reflect.KClass

@Suppress("unused") // TFragment нужен для вывода типов в toScreen
abstract class ScreenArgs<TFragment : ArgsFragmentFeature<*>>

class Screen<TFragment : ArgsFragmentFeature<*>, TArgs : ScreenArgs<TFragment>>(
    private val args: TArgs,
    private val fragmentClass: KClass<TFragment>
) : SupportAppScreen() {
    override fun getFragment(): Fragment = (fragmentClass.java.newInstance() as Fragment)
        .also {
            @Suppress("UNCHECKED_CAST")
            (it as ArgsFragmentFeature<TArgs>).args = args
        }
}

inline fun <reified TFragment : ArgsFragmentFeature<*>, TArgs : ScreenArgs<TFragment>> TArgs.toScreen() =
    Screen(args = this@toScreen, fragmentClass = TFragment::class)