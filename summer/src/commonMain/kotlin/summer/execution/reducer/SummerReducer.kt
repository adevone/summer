package summer.execution.reducer

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

abstract class SummerReducer<TEntity, TParams>(
    private val scope: CoroutineScope
) {
    private val lastDeferred = atomic<Deferred<TEntity>?>(initial = null)
    private val lastParams = atomic<TParams?>(initial = null)

    protected abstract suspend operator fun invoke(
        params: TParams,
        previous: TEntity
    ): TEntity

    protected abstract suspend fun initial(): TEntity

    suspend fun get(): TEntity = getAsync().await()

    fun <TDependEntity, TDependParams> depend(
        source: SummerReducer<TDependEntity, TDependParams>,
        action: suspend (
            depend: TDependEntity,
            dependParams: TDependParams?,
            previous: TEntity
        ) -> TEntity
    ) {
        source.observe(
            object : Observer<TDependEntity, TDependParams> {
                override fun onObtain(deferred: Deferred<TDependEntity>, params: TDependParams?) {

                    val previousDeferred = getAsync()
                    val newDeferred = scope.async {
                        val previous = previousDeferred.await()
                        val depend = deferred.await()
                        action(depend, params, previous)
                    }

                    @Suppress("DeferredResultUnused")
                    lastDeferred.getAndSet(newDeferred)

                    lastParams.getAndSet(null)

                    observers.forEach { observer ->
                        observer.onObtain(newDeferred, null)
                    }
                }
            }
        )
    }

    operator fun invoke(params: TParams) {

        val previousDeferred = getAsync()

        val deferred = scope.async {
            val previous = previousDeferred.await()
            invoke(params, previous)
        }

        @Suppress("DeferredResultUnused")
        lastDeferred.getAndSet(deferred)

        lastParams.getAndSet(params)

        observers.forEach { observer ->
            observer.onObtain(deferred, params)
        }
    }

    private val observers = mutableListOf<Observer<TEntity, TParams>>()

    internal fun observe(observer: Observer<TEntity, TParams>) {
        val deferred = getAsync()
        val params = lastParams.value
        observer.onObtain(deferred, params)
        observers.add(observer)
    }

    internal fun unsubscribe(observer: Observer<TEntity, TParams>) {
        observers.remove(observer)
    }

    internal fun getAsync(): Deferred<TEntity> {
        val lastDeferred = lastDeferred.value
        return if (lastDeferred != null) {
            lastDeferred
        } else {

            val initialDeferred = scope.async { initial() }

            @Suppress("DeferredResultUnused")
            this.lastDeferred.getAndSet(initialDeferred)

            initialDeferred
        }
    }

    internal interface Observer<TEntity, in TParams> {
        fun onObtain(deferred: Deferred<TEntity>, params: TParams?)
    }
}