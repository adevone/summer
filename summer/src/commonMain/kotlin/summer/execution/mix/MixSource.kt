package summer

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class MixSource<T, TSourceEntity, TMixEntity, TSourceParams>(
    private val transform: (TSourceEntity, TMixEntity) -> T,
    private val source: Source<TSourceEntity, TSourceParams>,
    val mix: SummerReducer<TMixEntity, *>,
    private val scope: CoroutineScope
) : SummerReducer.Observer<TMixEntity, Any?> {
    private val lastDeferredAtomic = atomic<Deferred<T>?>(null)

    private val lastSourceDeferredAtomic = atomic<Deferred<TSourceEntity>?>(null)
    private val lastSourceParamsAtomic = atomic<TSourceParams?>(null)

    override fun onObtain(deferred: Deferred<TMixEntity>, params: Any?) {
        val lastSourceParams = lastSourceParamsAtomic.value
        if (lastSourceParams != null) {
            @Suppress("DeferredResultUnused")
            obtain(lastSourceParams)
        }
    }

    lateinit var consumer: Consumer<T, TSourceParams>

    fun obtain(params: TSourceParams): Deferred<T> {

        lastSourceParamsAtomic.getAndSet(params)

        val sourceEntityDeferred = source.executeAsync(params, scope)

        @Suppress("DeferredResultUnused")
        lastSourceDeferredAtomic.getAndSet(sourceEntityDeferred)

        val previousDeferred = lastDeferredAtomic.value
        val deferred = scope.async {
            previousDeferred?.await()

            val mixEntityDeferred = mix.getAsync()

            val mixEntity = mixEntityDeferred.await()
            val sourceEntity = sourceEntityDeferred.await()

            transform(sourceEntity, mixEntity)
        }

        @Suppress("DeferredResultUnused")
        lastDeferredAtomic.getAndSet(deferred)

        consumer.onObtain(deferred, params)

        return deferred
    }

    sealed class Source<TEntity, TParams> {

        abstract fun executeAsync(params: TParams, scope: CoroutineScope): Deferred<TEntity>

        class Just<TEntity, TParams>(
            private val source: SummerSource<TEntity, TParams>
        ) : Source<TEntity, TParams>() {
            override fun executeAsync(
                params: TParams,
                scope: CoroutineScope
            ): Deferred<TEntity> = scope.async {
                source(params)
            }
        }

        class Mix<T, TSourceParams>(
            private val mixSource: MixSource<T, *, *, TSourceParams>
        ) : Source<T, TSourceParams>() {
            override fun executeAsync(
                params: TSourceParams,
                scope: CoroutineScope
            ): Deferred<T> {
                return mixSource.obtain(params)
            }
        }
    }

    interface Consumer<T, TSourceParams> {
        fun onObtain(deferred: Deferred<T>, sourceParams: TSourceParams)
    }
}