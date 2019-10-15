package summer

/**
 * Allows presenter to call another presenter for result
 *
 * Example:
 *
 * class BasePresenter : SummerPresenter<...>()
 *
 * class CodeHolder : ResultHolder<Int>()
 *
 * class CallerPresenter(
 *     private val codeHolder: CodeHolder
 * ) : BasePresenter() {
 *
 *     override fun onAppear() {
 *         codeHolder.pop()?.let { code ->
 *             if (code != null) {
 *                 // do something with code
 *             }
 *         }
 *     }
 *
 *     fun onToCodeClick() {
 *         router.toForResult()
 *     }
 * }
 *
 * interface CallerRouter {
 *     fun toResult()
 * }
 *
 * class ForResultPresenter(
 *     private val codeHolder: CodeHolder
 * ) : BasePresenter() {
 *
 *     override fun onEnter() {
 *         val code = 1
 *         codeHolder.put(code)
 *         router.toCaller()
 *     }
 * }
 *
 * interface ForResultRouter {
 *     fun toCaller()
 * }
 */
abstract class ResultHolder<T : Any> {

    private var value: T? = null

    fun put(value: T) {
        this.value = value
    }

    fun pop() = value?.also {
        value = null
    }
}