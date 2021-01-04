package summer

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Like [kotlin.properties.Delegates.observable] but with no initial value.
 * Can be used to observe [ViewProxyProvider.viewProxy] state changes in a view.
 *
 * Based on the example in [ViewProxyProvider]:
 *
 * class FeatureFragment : BaseFragment(), FeatureView {
 *
 *     override var prop: Int by didSet {
 *         myCounterView.text = prop.toString()
 *     }
 * }
 *
 * class BaseFragment : Fragment() {
 *     companion object : DidSetMixin
 * }
 *
 * Or just write "by DidSet" instead of "by didSet"
 * if you don't want to implement DidSetMixin on your base view
 */
open class DidSet<T>(
    private val onSet: (T) -> Unit
) : ReadWriteProperty<Any?, T> {

    private var value: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        @Suppress("UNCHECKED_CAST")
        return value as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        onSet(value)
    }
}

/**
 * Can be used to observe only not null changes in a view.
 *
 * @see DidSet
 */
class DidSetNotNull<T>(
    onSet: (T) -> Unit
) : DidSet<T?>(
    onSet = { value ->
        if (value != null) {
            onSet(value)
        }
    }
)

/**
 * Implement on a companion of your base view (BaseFragment, BaseActivity)
 * to not import [DidSet] and [DidSetNotNull] in each feature view
 */
interface DidSetMixin {
    fun <T> didSet(onSet: (T) -> Unit) = DidSet(onSet)
    fun <T> didSetNotNull(onSet: (T) -> Unit) = DidSetNotNull(onSet)
}