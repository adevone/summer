package summer.execution.mix

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import summer.SummerPresenter
import summer.execution.DeferredExecutor
import summer.execution.SummerExecutor
import summer.execution.SummerExecutorInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * Executes [MixSource] in [workContext] and returns value in [scope] context
 * Must be used from exactly one thread
 */
class MixSourceExecutor<T, TSourceParams> internal constructor(
    private val source: MixSource<T, *, *, TSourceParams>,
    private val deferredExecutor: DeferredExecutor,
    private val interceptor: SummerExecutorInterceptor<T, TSourceParams>,
    private val onExecute: suspend (TSourceParams) -> Unit,
    private val onFailure: suspend (Throwable, TSourceParams) -> Unit,
    private val onCancel: suspend (TSourceParams) -> Unit,
    private val onSuccess: suspend (T, TSourceParams) -> Unit,
    private val scope: CoroutineScope,
    private val workContext: CoroutineContext
) : MixSource.Consumer<T, TSourceParams> {

    /**
     * Executes [source]. Emits value in [onSuccess] or [onFailure] only if
     * parent [SummerPresenter] (or any another [SummerExecutor])
     * is not destroyed
     */
    fun execute(params: TSourceParams) {
        @Suppress("DeferredResultUnused")
        source.obtain(params)
    }

    override fun onObtain(deferred: Deferred<T>, sourceParams: TSourceParams) {
        deferredExecutor.launch(
            initialScope = scope,
            params = sourceParams,
            onFailure = onFailure
        ) {
            withContext(workContext) {
                deferredExecutor.execute(
                    deferred = deferred,
                    params = sourceParams,
                    interceptor = interceptor,
                    onExecute = onExecute,
                    onSuccess = onSuccess,
                    onCancel = onCancel
                )
            }
        }
    }
}

fun <T> MixSourceExecutor<T, Unit>.execute() = execute(Unit)