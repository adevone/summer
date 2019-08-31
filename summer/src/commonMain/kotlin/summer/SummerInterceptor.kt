package summer

import kotlin.reflect.KMutableProperty0

abstract class SummerExecutorInterceptor<TEntity, TParams> {

    open suspend fun onEvent(event: Event<TEntity, TParams>) {}

    open suspend fun onExecute(event: Event.Executed<TEntity, TParams>) {}

    open suspend fun onCompleted(event: Event.Completed<TEntity, TParams>) {}

    open suspend fun onSuccess(event: Event.Completed.Success<TEntity, TParams>) {}

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

class NoInterceptor<TEntity, TParams> : SummerExecutorInterceptor<TEntity, TParams>()

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