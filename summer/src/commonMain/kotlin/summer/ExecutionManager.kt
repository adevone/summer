package summer

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred

internal class ExecutionManager(
    private val logger: SummerLogger
) {
    suspend fun <TEntity, TParams> handleDeferred(
        deferred: Deferred<TEntity>,
        params: TParams,
        interceptor: SummerExecutorInterceptor<TEntity, TParams>,
        onExecute: suspend (_: TParams) -> Unit,
        onFailure: suspend (Throwable, _: TParams) -> Unit,
        onSuccess: suspend (TEntity, TParams) -> Unit
    ) {
        try {
            interceptor.onExecute(SummerExecutorInterceptor.Event.Executed(params))
            onExecute(params)
            val result = try {
                deferred.await()
            } catch (e: Throwable) {
                val event = SummerExecutorInterceptor.Event.Completed.Failure<TEntity, TParams>(e, params)
                interceptor.onCompleted(event)
                interceptor.onFailure(event)
                throw e
            }
            val event = SummerExecutorInterceptor.Event.Completed.Success(result, params)
            interceptor.onCompleted(event)
            interceptor.onSuccess(event)
            onSuccess(result, params)
        } catch (e: CancellationException) {
            logger.info { "$this cancelled" }
        } catch (e: Throwable) {
            onFailure(e, params)
        }
    }
}