package summer.example

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.debt_fragment.*
import org.kodein.di.erased.instance
import summer.SummerPresenter
import summer.android.SummerFragment
import summer.example.debt.MainActivity
import summer.summer.StateHolder

abstract class ScreenFragment<
        TViewState : Any,
        TViewMethods : Any,
        TRouter : Any,
        TPresenter : SummerPresenter<TViewState, TViewMethods, TRouter>> :
    SummerFragment<TViewState, TViewMethods, TRouter, TPresenter>(),
    AppKodeinAware {

    override val stateHolder by instance<StateHolder>()

    protected open val screenToolbar: Toolbar? get() = toolbar!!
    protected open val backButtonIconResource: Int? = null
    protected open val isBottomMenuVisible: Boolean = true

    open val layoutRes: Int = 0

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        layoutRes,
        container,
        false
    )!!.also { view ->
        if (view.background == null) {
            view.setBackgroundColor(Color.WHITE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyToolbar()
    }

    private fun applyToolbar() {
        val mainActivity = context as? MainActivity
        val toolbar = screenToolbar
        if (mainActivity != null) {
            if (toolbar != null) {
                mainActivity.setSupportActionBar(toolbar)
                val actionBar = mainActivity.supportActionBar
                if (actionBar != null) {
                    if (backButtonIconResource != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true)
                        actionBar.setHomeAsUpIndicator(backButtonIconResource!!)
                    } else {
                        actionBar.setDisplayHomeAsUpEnabled(false)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }
}