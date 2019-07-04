package summer.example

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard(of: View? = null) = requireContext().hideKeyboard(of)

fun Context.hideKeyboard(of: View? = null) {

    val token = if (of != null) {
        of.windowToken
    } else {
        (this as Activity).currentFocus?.windowToken
    }

    if (token != null) {
        getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(token, 0)
    }
}