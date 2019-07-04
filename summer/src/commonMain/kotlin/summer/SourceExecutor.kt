package summer.summer

import kotlinx.coroutines.*
import summer.SummerSource
import summer.log.KLogging
import kotlin.coroutines.CoroutineContext

class SourceExecutor<TEntity, TParams>(
    private val source: SummerSource<TEntity, TParams>,
    private val action: suspend (Deferred<TEntity>, TParams) -> Unit,
    private val scope: CoroutineScope,
    private val uiContext: CoroutineContext
) {
    private var jobs = mutableMapOf<TParams, Job>()
    fun execute(params: TParams, cancelAll: Boolean = true) {
        if (cancelAll) {
            cancelAll()
        }
        val job = GlobalScope.launch(uiContext) {
            try {
                val deferred = scope.async {
                    source.execute(params)
                }
                action(deferred, params)
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
                job.cancelChildren()
            }
        }
    }

    fun cancel(params: TParams) {
        jobs[params]?.cancelChildren()
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