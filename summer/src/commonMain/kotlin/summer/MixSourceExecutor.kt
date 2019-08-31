package summer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MixSourceExecutor<T, TSourceParams> internal constructor(
    private val source: MixSource<T, *, *, TSourceParams>,
    private val executionManager: ExecutionManager,
    private val interceptor: SummerExecutorInterceptor<T, TSourceParams>,
    private val onExecuted: suspend (_: TSourceParams) -> Unit,
    private val onFailure: suspend (Throwable, _: TSourceParams) -> Unit,
    private val onSuccess: suspend (T, TSourceParams) -> Unit,
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
                    onFailure = onFailure,
                    onExecuted = onExecuted,
                    onSuccess = onSuccess
                )
            }
        }
    }

    fun execute(params: TSourceParams) {
        @Suppress("DeferredResultUnused")
        source.obtain(params)
    }
}

fun <T> MixSourceExecutor<T, Unit>.execute() = execute(Unit)