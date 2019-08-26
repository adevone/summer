package summer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import summer.log.KLogging
import kotlin.coroutines.CoroutineContext

class MixSourceExecutor<T, TSourceParams> internal constructor(
    private val source: MixSource<T, *, *, TSourceParams>,
    private val executionManager: ExecutionManager,
    private val interceptor: SummerExecutorInterceptor<T, TSourceParams>,
    private val onError: suspend (Throwable, _: TSourceParams) -> Unit = { e, _ -> throw e },
    private val onComplete: suspend (T, TSourceParams) -> Unit = { _, _ -> },
    private val scope: CoroutineScope,
    private val workContext: CoroutineContext
) : MixSource.Consumer<T, TSourceParams> {

    override fun onObtain(deferred: Deferred<T>, sourceParams: TSourceParams) {
        scope.launch {
            withContext(coroutineContext + workContext) {
                executionManager.handleDeferred(
                    deferred = deferred,
                    params = sourceParams,
                    interceptor = interceptor,
                    onError = onError,
                    onComplete = onComplete
                )
            }
        }
    }

    fun execute(params: TSourceParams) {
        @Suppress("DeferredResultUnused")
        source.obtain(params)
    }

    companion object : KLogging()
}

fun <T> MixSourceExecutor<T, Unit>.execute() = execute(Unit)