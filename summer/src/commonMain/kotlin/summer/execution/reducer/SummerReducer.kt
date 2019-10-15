package summer.execution.reducer

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

/**
 * Source that can produce new state from previous one.
 *
 * Example (counter):
 *
 * typealias Reducer = SummerReducer
 *
 * class Counter : Reducer<Int, Counter.Params> {
 *
 *     override suspend fun initial(): Int = 0
 *
 *     override suspend fun next(params: Params, previous: Int): Int = when (params) {
 *         Params.Increase -> previous + 1
 *         Params.Decrease -> previous - 1
 *     }
 *
 *     sealed class Params {
 *         object Increase : Params()
 *         object Decrease : Params()
 *     }
 * }
 *
 * Example (pagination):
 *
 * data class Product(
 *     val price: Double
 * )
 *
 * class LoadProducts(
 *     private val loadFromDb: LoadProductsFromDb,
 *     private val loadFromNet: LoadProductsFromNet
 * ) : Reducer<List<Product>, LoadProducts.Params> {
 *
 *     override suspend fun initial(): List<Product> = loadFromDb()
 *
 *     override suspend fun next(params: Params, previous: List<Product>): List<Product> {
 *         val nextProducts = loadFromNet(LoadProductsFromNet.Params(count = params.count))
 *         return nextProducts + previous
 *     }
 *
 *     data class Params(
 *         val count: Int
 *     )
 *
 *     class Factory(
 *         private val loadFromDb: LoadProductsFromDb,
 *         private val loadFromNet: LoadProductsFromNet
 *     ) {
 *         fun create() = LoadProducts(loadFromDb, loadFromNet)
 *     }
 * }
 */
abstract class SummerReducer<TEntity, TParams>(
    private val scope: CoroutineScope
) {
    /**
     * Called on first [next], [get] or [depend] call
     * @return first value for reducer
     * For instance you can load data from database in this method
     */
    protected abstract suspend fun initial(): TEntity

    /**
     * Called every times when [invoke] is called
     * @return new value that can depend from previous
     */
    protected abstract suspend fun next(
        params: TParams,
        previous: TEntity
    ): TEntity

    private val lastDeferred = atomic<Deferred<TEntity>?>(initial = null)
    private val lastParams = atomic<TParams?>(initial = null)

    /**
     * External interface of reducer. Can be used to call it. Creates new operation
     */
    operator fun invoke(params: TParams) {

        val previousDeferred = getAsync()

        val deferred = scope.async {
            val previous = previousDeferred.await()
            next(params, previous)
        }

        @Suppress("DeferredResultUnused")
        lastDeferred.getAndSet(deferred)

        lastParams.getAndSet(params)

        observers.forEach { observer ->
            observer.onObtain(deferred, params)
        }
    }

    /**
     * Get current value of reducer
     * @return result of last created operation before the call of [get]
     */
    suspend fun get(): TEntity = getAsync().await()

    /**
     * Depend on another [SummerReducer]
     * @param action specifies which action will be performed on each new value of [source].
     * [action] triggers first times immediately after [depend] call
     */
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