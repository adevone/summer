package summer.execution.source

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import summer.execution.DeferredExecutor
import summer.execution.SummerExecutorInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * Does not support multithreading
 */
class SourceExecutor<TEntity, TParams> internal constructor(
    private val source: SummerSource<TEntity, TParams>,
    private val deferredExecutor: DeferredExecutor,
    private val interceptor: SummerExecutorInterceptor<TEntity, TParams>,
    private val onExecute: suspend (_: TParams) -> Unit,
    private val onFailure: suspend (Throwable, _: TParams) -> Unit,
    private val onCancel: suspend (TParams) -> Unit,
    private val onSuccess: suspend (TEntity, _: TParams) -> Unit,
    private val scope: CoroutineScope,
    private val workContext: CoroutineContext
) {
    fun execute(params: TParams, cancelAll: Boolean = true) {
        if (cancelAll) {
            cancelAll()
        }
        val job = scope.launch {
            try {
                val deferred = async(workContext) {
                    source(params)
                }
                deferredExecutor.execute(
                    deferred = deferred,
                    params = params,
                    interceptor = interceptor,
                    onExecute = onExecute,
                    onSuccess = onSuccess,
                    onFailure = onFailure,
                    onCancel = onCancel
                )
            } finally {
                jobs.remove(params)
            }
        }
        onExecute(job, params)
    }

    private var jobs = mutableMapOf<TParams, MutableList<Job>>()
    private fun onExecute(job: Job, params: TParams) {
        if (params !in jobs) jobs[params] = mutableListOf()
        jobs[params]!!.add(job)
    }

    fun cancelAll(needCancel: (params: TParams) -> Boolean = { true }) {
        jobs.forEach { (params, jobs) ->
            jobs.forEach { job ->
                val willCancel = needCancel(params)
                if (willCancel) {
                    job.cancel()
                }
            }
        }
    }

    fun cancel(params: TParams) {
        jobs[params]?.forEach { job ->
            job.cancel()
        }
    }

    fun isAnyExecuted(): Boolean {
        return jobs.any { (_, jobs) ->
            jobs.any { job ->
                job.isActive
            }
        }
    }

    fun isAnyExecuted(params: TParams): Boolean {
        return jobs[params]?.any { job ->
            job.isActive
        } == true
    }
}

fun <TEntity> SourceExecutor<TEntity, Unit>.execute(
    cancelAll: Boolean = true
) = execute(
    params = Unit,
    cancelAll = cancelAll
)