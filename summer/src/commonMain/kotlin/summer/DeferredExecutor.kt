package summer

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withContext

internal class DeferredExecutor(
    private val logger: SummerLogger,
    private val uiScope: CoroutineScope
) {
    suspend fun <TEntity, TParams> execute(
        deferred: Deferred<TEntity>,
        params: TParams,
        interceptor: SummerExecutorInterceptor<TEntity, TParams>,
        onExecute: suspend (_: TParams) -> Unit,
        onFailure: suspend (Throwable, _: TParams) -> Unit,
        onSuccess: suspend (TEntity, TParams) -> Unit
    ) {
        try {
            withContext(uiScope.coroutineContext) {
                val executedEvent = SummerExecutorInterceptor.Event.Executed<TEntity, TParams>(params)
                interceptor.onEvent(executedEvent)
                interceptor.onExecute(executedEvent)
                onExecute(params)
            }
            val result = try {
                deferred.await()
            } catch (e: Throwable) {
                withContext(uiScope.coroutineContext) {
                    val event = SummerExecutorInterceptor.Event.Completed.Failure<TEntity, TParams>(e, params)
                    interceptor.onEvent(event)
                    interceptor.onCompleted(event)
                    interceptor.onFailure(event)
                }
                throw e
            }
            withContext(uiScope.coroutineContext) {
                val successEvent = SummerExecutorInterceptor.Event.Completed.Success(result, params)
                interceptor.onEvent(successEvent)
                interceptor.onCompleted(successEvent)
                interceptor.onSuccess(successEvent)
                onSuccess(result, params)
            }
        } catch (e: CancellationException) {
            logger.info { "$this cancelled" }
        } catch (e: Throwable) {
            withContext(uiScope.coroutineContext) {
                onFailure(e, params)
            }
        }
    }
}