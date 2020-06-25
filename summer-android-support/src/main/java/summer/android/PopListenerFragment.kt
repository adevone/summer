package summer.android

import android.support.v4.app.Fragment

abstract class PopListenerFragment : Fragment() {

    open fun onPop() {}

    override fun onDestroy() {
        notifyPresenterIfRemoving()
        super.onDestroy()
    }

    private fun notifyPresenterIfRemoving() {
        var anyParentIsRemoving = false

        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            onPop()
        }
    }
}