package summer.summer

import kotlinx.coroutines.*
import summer.SummerSharedSource
import summer.log.KLogging
import kotlin.coroutines.CoroutineContext

class SharedSourceExecutor<TEntity, in TParams>(
    private val source: SummerSharedSource<TEntity, TParams>,
    private val action: suspend (Deferred<TEntity>, TParams?) -> Unit,
    private val scope: CoroutineScope,
    private val uiContext: CoroutineContext
) : SummerSharedSource.Observer<TEntity, TParams> {

    override fun onObtain(deferred: Deferred<TEntity>, params: TParams?) {
        GlobalScope.launch(uiContext) {
            val scopedDeferred = scope.async {
                deferred.await()
            }
            action(scopedDeferred, params)
        }
    }

    fun execute(params: TParams) {
        source.execute(params)
    }

    companion object : KLogging()
}

fun <TEntity> SharedSourceExecutor<TEntity, Unit>.execute() = execute(Unit)