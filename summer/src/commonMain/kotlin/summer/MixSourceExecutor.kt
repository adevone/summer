package summer.summer

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import summer.log.KLogging
import kotlin.coroutines.CoroutineContext

class MixSourceExecutor<T, TSourceParams>(
    private val source: MixSource<T, *, *, TSourceParams>,
    private val action: suspend (Deferred<T>, TSourceParams) -> Unit,
    private val uiContext: CoroutineContext
) : MixSource.Consumer<T, TSourceParams> {

    override fun onObtain(deferred: Deferred<T>, sourceParams: TSourceParams) {
        GlobalScope.launch(uiContext) {
            action(deferred, sourceParams)
        }
    }

    fun execute(params: TSourceParams) {
        source.obtain(params)
    }

    companion object : KLogging()
}

fun <T> MixSourceExecutor<T, Unit>.execute() = execute(Unit)