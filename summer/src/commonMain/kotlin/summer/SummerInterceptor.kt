package summer

import kotlin.reflect.KMutableProperty0

abstract class SummerExecutorInterceptor<TEntity, TParams> {

    open suspend fun onEvent(event: Event) {}

    open suspend fun onExecuted(event: Event.Executed<TParams>) {}

    open suspend fun onCompleted(event: Event.Completed<TParams>) {}

    open suspend fun onSuccess(event: Event.Completed.Success<TEntity, TParams>) {}

    open suspend fun onFailure(event: Event.Completed.Failure<TParams>) {}

    sealed class Event {

        data class Executed<TParams>(
            val params: TParams
        ) : Event()

        sealed class Completed<TParams> : Event() {
            abstract val params: TParams

            data class Success<TEntity, TParams>(
                val entity: TEntity,
                override val params: TParams
            ) : Completed<TParams>()

            data class Failure<TParams>(
                val e: Throwable,
                override val params: TParams
            ) : Completed<TParams>()
        }
    }
}

class NoInterceptor<TEntity, TParams> : SummerExecutorInterceptor<TEntity, TParams>()

class LoadingExecutorInterceptor<TParams>(
    private val getProperty: suspend () -> KMutableProperty0<Boolean>,
    private val needShow: suspend (event: Event.Executed<TParams>) -> Boolean
) : SummerExecutorInterceptor<Any?, TParams>() {

    override suspend fun onExecuted(event: Event.Executed<TParams>) {
        val property = getProperty()
        val needShowLoading = needShow(event)
        if (needShowLoading) {
            property.set(true)
        }
    }

    override suspend fun onCompleted(event: Event.Completed<TParams>) {
        val property = getProperty()
        property.set(false)
    }
}