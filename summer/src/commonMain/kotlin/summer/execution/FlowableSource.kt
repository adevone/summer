package summer.execution

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import summer.defaultBackgroundContext
import kotlin.coroutines.CoroutineContext

/**
 * Source you can observe using [Flow].
 * To subscribe to it updates use [SummerExecutor.flow] extension.
 * Example:

    class ProductsFlow (
        workContext: CoroutineContext,
        private val getProductsRequest: GetProducts
    ) : FlowableUseCase<List<Product>, ProductsFlow.Params>(workContext) {

        override suspend fun next(params: Params): List<Product> {
            return getProducts(params.region)
        }

        data class Params(val filter: Filter)
    }

    class Presenter(
        ...
        productsFlow: ProductsFlow
    ) {
        ...
        override fun onEnter() {
            getOfferClusters
                .flow() // [SummerExecutor.flow] extension.
                .catch { e ->
                    logger.error(e)
                }
                .onEach { products ->
                    viewStateProxy.products = products
                }
                .launchIn(this)
        }
    }
 */

abstract class FlowableSource<TEntity, TParams>(
    coroutineScope: CoroutineScope = CoroutineScope(defaultBackgroundContext + SupervisorJob())
) : CoroutineScope by coroutineScope {

    constructor(
        workContext: CoroutineContext = defaultBackgroundContext
    ) : this(CoroutineScope(workContext + SupervisorJob()))

    constructor(
        parentScope: CoroutineScope,
        workContext: CoroutineContext = defaultBackgroundContext
    ) : this(parentScope + workContext)

    private var lastParams: TParams? = null
    private val channel = ConflatedBroadcastChannel<Deferred<TEntity>>()

    protected abstract suspend fun next(params: TParams, previousResult: PreviousResult<TEntity>?): TEntity

    /**
     * @return new [Flow] of deferred value.
     * Each time a new value is produced (calling [invoke] or [emit] methods),
     * it is sent to all non completed flow.
     * Deferred value use to expose all exceptions to subscription site.
     * Use [SummerExecutor.flow] extension to get flow of awaited [TEntity].
     */
    internal fun flow(): Flow<Deferred<TEntity>> = channel.asFlow()

    /**
     * Trigger computation of a new value.
     */
    operator fun invoke(params: TParams) {
        lastParams = params
        emit(::next)
    }

    /**
     * Send new value to all [Flow] received from [flow] method.
     */
    fun emit(computeNext: suspend (params: TParams, previousResult: PreviousResult<TEntity>?) -> TEntity) {
        lastParams?.let { params ->
            launch {
                val previousResult = getPreviousResult()
                val deferred = async {
                    computeNext(params, previousResult)
                }
                channel.send(deferred)
            }
        }
    }

    private suspend fun getPreviousResult(): PreviousResult<TEntity>? {
        val previousDeferred = channel.valueOrNull ?: return null
        return try {
            PreviousResult.Success(previousDeferred.await())
        } catch (e: Throwable) {
            PreviousResult.Failure(e)
        }
    }

    sealed class PreviousResult<TEntity> {
        data class Success<TEntity>(val result: TEntity) : PreviousResult<TEntity>()
        data class Failure<TEntity>(val exception: Throwable) : PreviousResult<TEntity>()
    }
}

/**
 * Depend on another [Flow].
 * @param computeNext specifies how to compute new value depending on [flow].
 *
 * Example:

  class ProductsFlow (
       workContext: CoroutineContext,
       private val getProducts: GetProducts,
       private val filterStore: FilterStore
  ) : FlowableUseCase<List<Product>, ProductsFlow.Params>(workContext) {

        init {
            scope.launch {
                dependOn(filterStore.flow()) { params, filter ->
                    getProducts(params.region, filter)
                }
            }
        }

        override suspend fun next(params: Params, previousResult: PreviousResult<List<Product>>?): List<Product> {
            return getProducts(
                region = params.region,
                filter = filterStore.value
            )
        }

        private suspend fun getProducts(region: MapRegion, filter: Filter) : List<Product> {
            return getProducts(
                GetProducts.Params(
                    region = region,
                    offersSearchFilter = filter
                )
            )
        }

        data class Params(val region: MapRegion)
    }
 */
suspend fun <TEntity, TParams, TDependEntity> FlowableSource<TEntity, TParams>.dependOn(
    flow: Flow<TDependEntity>,
    computeNext: suspend (TParams, TDependEntity, FlowableSource.PreviousResult<TEntity>?) -> TEntity
) {
    flow.collect { dependEntity ->
        this@dependOn.emit { params, previousResult ->
            computeNext(params, dependEntity, previousResult)
        }
    }
}