package summer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import summer.log.KLogging
import kotlin.coroutines.CoroutineContext

class SourceExecutor<TEntity, TParams> internal constructor(
    private val source: SummerSource<TEntity, TParams>,
    private val executionManager: ExecutionManager,
    private val interceptor: SummerExecutorInterceptor<TEntity, TParams>,
    private val onError: suspend (Throwable, _: TParams) -> Unit,
    private val onComplete: suspend (TEntity, _: TParams) -> Unit,
    private val scope: CoroutineScope,
    private val workContext: CoroutineContext
) {
    private var jobs = mutableMapOf<TParams, Job>()
    fun execute(params: TParams, cancelAll: Boolean = true) {
        if (cancelAll) {
            cancelAll()
        }
        val job = scope.launch {
            try {
                val deferred = async(workContext) {
                    source(params)
                }
                executionManager.handleDeferred(
                    deferred = deferred,
                    params = params,
                    interceptor = interceptor,
                    onError = onError,
                    onComplete = onComplete
                )
            } finally {
                jobs.remove(params)
            }
        }
        jobs[params] = job
    }

    fun cancelAll(needCancel: (params: TParams) -> Boolean = { true }) {
        jobs.forEach { (params, job) ->
            val willCancel = needCancel(params)
            if (willCancel) {
                job.cancel()
            }
        }
    }

    fun cancel(params: TParams) {
        jobs[params]?.cancel()
    }

    fun isAnyExecuted() = jobs.any { (_, job) -> job.isActive }

    fun isExecuted(params: TParams) = jobs[params]?.isActive

    companion object : KLogging()
}

fun <TEntity> SourceExecutor<TEntity, Unit>.execute(
    cancelAll: Boolean = true
) = execute(
    Unit,
    cancelAll
)