package summer

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import summer.log.LogCollector

internal class ExecutionManager(
    private val logger: LogCollector
) {
    suspend fun <TEntity, TParams> handleDeferred(
        deferred: Deferred<TEntity>,
        params: TParams,
        interceptor: SummerExecutorInterceptor<TEntity, TParams>,
        onExecuted: suspend (_: TParams) -> Unit,
        onFailure: suspend (Throwable, _: TParams) -> Unit,
        onSuccess: suspend (TEntity, TParams) -> Unit
    ) {
        try {
            interceptor.onExecuted(SummerExecutorInterceptor.Event.Executed(params))
            onExecuted(params)
            val result = try {
                deferred.await()
            } catch (e: Throwable) {
                interceptor.onFailure(SummerExecutorInterceptor.Event.Completed.Failure(e, params))
                throw e
            }
            interceptor.onSuccess(SummerExecutorInterceptor.Event.Completed.Success(result, params))
            onSuccess(result, params)
        } catch (e: CancellationException) {
            logger.info { "$this cancelled" }
        } catch (e: Throwable) {
            onFailure(e, params)
        }
    }
}