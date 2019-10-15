package summer.execution

import summer.execution.mix.MixSourceExecutor
import summer.execution.reducer.ReducerExecutor
import summer.execution.source.SourceExecutor
import kotlin.reflect.KMutableProperty0

/**
 * Allows intercept executors ([SourceExecutor], [MixSourceExecutor] or [ReducerExecutor]) events
 */
abstract class SummerExecutorInterceptor<TEntity, TParams> {

    /**
     * Called every times when any event occurred
     */
    open suspend fun onEvent(event: Event<TEntity, TParams>) {}

    /**
     * Called immediately after executor execution
     */
    open suspend fun onExecute(event: Event.Executed<TEntity, TParams>) {}

    /**
     * Called when execution successfully completed or failed
     */
    open suspend fun onCompleted(event: Event.Completed<TEntity, TParams>) {}

    /**
     * Called when execution successfully completed
     */
    open suspend fun onSuccess(event: Event.Completed.Success<TEntity, TParams>) {}

    /**
     * Called when execution failed
     */
    open suspend fun onFailure(event: Event.Completed.Failure<TEntity, TParams>) {}

    sealed class Event<TEntity, TParams> {

        data class Executed<TEntity, TParams>(
            val params: TParams
        ) : Event<TEntity, TParams>()

        sealed class Completed<TEntity, TParams> : Event<TEntity, TParams>() {
            abstract val params: TParams

            data class Success<TEntity, TParams>(
                val entity: TEntity,
                override val params: TParams
            ) : Completed<TEntity, TParams>()

            data class Failure<TEntity, TParams>(
                val e: Throwable,
                override val params: TParams
            ) : Completed<TEntity, TParams>()
        }
    }
}

/**
 * Do not intercept any events. Can be used as stub
 */
class NoInterceptor<TEntity, TParams> : SummerExecutorInterceptor<TEntity, TParams>()

/**
 * Emits true in result of [getProperty] when executor executed but only if [needShow] returned true
 * Emits false in result of [getProperty] when execution completed with any result (success or failure)
 */
class LoadingExecutorInterceptor<TEntity, TParams>(
    private val getProperty: suspend () -> KMutableProperty0<Boolean>,
    private val needShow: suspend (event: Event.Executed<TEntity, TParams>) -> Boolean
) : SummerExecutorInterceptor<TEntity, TParams>() {

    override suspend fun onExecute(event: Event.Executed<TEntity, TParams>) {
        val property = getProperty()
        val needShowLoading = needShow(event)
        if (needShowLoading) {
            property.set(true)
        }
    }

    override suspend fun onCompleted(event: Event.Completed<TEntity, TParams>) {
        val property = getProperty()
        property.set(false)
    }
}