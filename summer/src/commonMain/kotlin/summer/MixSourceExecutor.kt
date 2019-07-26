package summer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import summer.log.KLogging

class MixSourceExecutor<T, TSourceParams>(
    private val source: MixSource<T, *, *, TSourceParams>,
    private val action: suspend (Deferred<T>, TSourceParams) -> Unit,
    private val scope: CoroutineScope
) : MixSource.Consumer<T, TSourceParams> {

    override fun onObtain(deferred: Deferred<T>, sourceParams: TSourceParams) {
        scope.launch {
            action(deferred, sourceParams)
        }
    }

    fun execute(params: TSourceParams) {
        source.obtain(params)
    }

    companion object : KLogging()
}

fun <T> MixSourceExecutor<T, Unit>.execute() = execute(Unit)