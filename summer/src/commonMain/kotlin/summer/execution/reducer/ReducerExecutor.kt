package summer.execution.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import summer.SummerPresenter
import summer.execution.DeferredExecutor
import summer.execution.SummerExecutor
import summer.execution.SummerExecutorInterceptor
import summer.execution.withErrorHandler
import kotlin.coroutines.CoroutineContext

/**
 * Executes [SummerReducer] in [workContext] and returns value in [scope] context
 * Must be used from exactly one thread
 */
class ReducerExecutor<TEntity, TParams> internal constructor(
    internal val source: SummerReducer<TEntity, TParams>,
    private val deferredExecutor: DeferredExecutor,
    private val workContext: CoroutineContext,
    private val interceptor: SummerExecutorInterceptor<TEntity, TParams?>,
    private val onExecute: suspend (_: TParams?) -> Unit,
    private val onFailure: suspend (Throwable, _: TParams?) -> Unit,
    private val onCancel: suspend (TParams?) -> Unit,
    private val onSuccess: suspend (TEntity, _: TParams?) -> Unit,
    private val scope: CoroutineScope
) : SummerReducer.Observer<TEntity, TParams> {

    /**
     * Executes [source]. Emits value in [onSuccess] or [onFailure] only if
     * parent [SummerPresenter] (or any another [SummerExecutor])
     * is not destroyed
     */
    fun execute(params: TParams) {
        source(params)
    }

    override fun onObtain(deferred: Deferred<TEntity>, params: TParams?) {
        scope.withErrorHandler(
            onFailure = { e ->
                onFailure(e, params)
            }
        ).launch {
            val scopedDeferred = async(workContext) {
                deferred.await()
            }
            deferredExecutor.execute(
                deferred = scopedDeferred,
                params = params,
                interceptor = interceptor,
                onExecute = onExecute,
                onSuccess = onSuccess,
                onCancel = onCancel
            )
        }
    }
}

fun <TEntity> ReducerExecutor<TEntity, Unit>.execute() = execute(Unit)