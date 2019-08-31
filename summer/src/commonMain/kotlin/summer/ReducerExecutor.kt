package summer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ReducerExecutor<TEntity, in TParams> internal constructor(
    private val source: SummerReducer<TEntity, TParams>,
    private val executionManager: ExecutionManager,
    private val interceptor: SummerExecutorInterceptor<TEntity, TParams?>,
    private val onExecute: suspend (_: TParams?) -> Unit,
    private val onFailure: suspend (Throwable, _: TParams?) -> Unit,
    private val onSuccess: suspend (TEntity, _: TParams?) -> Unit,
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
                onFailure = onFailure,
                onExecute = onExecute,
                onSuccess = onSuccess
            )
        }
    }

    fun execute(params: TParams) {
        source(params)
    }
}

fun <TEntity> ReducerExecutor<TEntity, Unit>.execute() = execute(Unit)