package summer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import summer.log.KLogging
import kotlin.coroutines.CoroutineContext

class ReducerExecutor<TEntity, in TParams> internal constructor(
    private val source: SummerReducer<TEntity, TParams>,
    private val executionManager: ExecutionManager,
    private val interceptor: SummerExecutorInterceptor<TEntity, TParams?>,
    private val onError: suspend (Throwable, _: TParams?) -> Unit,
    private val onComplete: suspend (TEntity, _: TParams?) -> Unit,
    private val scope: CoroutineScope,
    private val workContext: CoroutineContext
) : SummerReducer.Observer<TEntity, TParams> {

    override fun onObtain(deferred: Deferred<TEntity>, params: TParams?) {
        scope.launch {
            val scopedDeferred = async(workContext) {
                deferred.await()
            }
            executionManager.handleDeferred(
                deferred = scopedDeferred,
                params = params,
                interceptor = interceptor,
                onError = onError,
                onComplete = onComplete
            )
        }
    }

    fun execute(params: TParams) {
        source(params)
    }

    companion object : KLogging()
}

fun <TEntity> ReducerExecutor<TEntity, Unit>.execute() = execute(Unit)