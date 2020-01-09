package summer.execution

import kotlinx.coroutines.*

internal class DeferredExecutor(
    private val uiScope: CoroutineScope
) {
    suspend fun <TEntity, TParams> execute(
        deferred: Deferred<TEntity>,
        params: TParams,
        interceptor: SummerExecutorInterceptor<TEntity, TParams>,
        onExecute: suspend (TParams) -> Unit,
        onSuccess: suspend (TEntity, TParams) -> Unit,
        onCancel: suspend (TParams) -> Unit
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
            withContext(uiScope.coroutineContext) {
                onCancel(params)
            }
        }
    }
}

/**
 * Decorate receiver [CoroutineScope] with [CoroutineExceptionHandler].
 * @param onFailure called inside receiver [CoroutineScope].
 */
internal fun CoroutineScope.withErrorHandler(onFailure: suspend (Throwable) -> Unit): CoroutineScope {
    val scope = this
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        scope.launch {
            onFailure(throwable)
        }
    }
    return scope + coroutineExceptionHandler
}