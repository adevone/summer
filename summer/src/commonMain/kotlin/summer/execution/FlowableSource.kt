package summer.execution

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
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
            launch {
                getOfferClusters
                    .flow() // [SummerExecutor.flow] extension.
                    .onEach { clusters ->
                        viewStateProxy.clusters = clusters
                    }
                    .catch { e ->
                        logger.error(e)
                    }
                    .collect()
            }
        }
    }
 */

abstract class FlowableSource<TEntity, TParams>(
    private val workContext: CoroutineContext
) {
    private var lastParams: TParams? = null
    private val channel = ConflatedBroadcastChannel<Deferred<TEntity>>()

    protected val scope = CoroutineScope(workContext)

    protected abstract suspend fun next(params: TParams): TEntity

    /**
     * @return new [Flow] of deferred value.
     * Each time a new value is produced (calling [invoke] or [emit] methods),
     * it is sent to all non completed flow.
     * Deferred value use to expose all exceptions to subscription site.
     * Use [SummerExecutor.flow] extension to get unwrapped [Flow] of [TEntity].
     */
    internal fun flow(): Flow<Deferred<TEntity>> = channel.asFlow().flowOn(workContext)

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
    fun emit(computeNext: suspend (TParams) -> TEntity) {
        lastParams?.let { params ->
            val deferred = scope.async {
                computeNext(params)
            }
            channel.offer(deferred)
        }
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
       private val getFilter: GetFilter
  ) : FlowableUseCase<List<Product>, ProductsFlow.Params>(workContext) {

        init {
            scope.launch {
                dependOn(getFilter.flow()) { params, filter ->
                    getClusters(params.region, filter)
                }
            }
        }

        override suspend fun next(params: Params): List<Product> {
            return getProducts(
                region = params.region,
                filter = getFilter.value
            )
        }

        private suspend fun getProducts(region: MapRegion, filter: Filter) : List<Cluster> {
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
    computeNext: suspend (TParams, TDependEntity) -> TEntity
) {
    flow.collect { dependEntity ->
        this@dependOn.emit { params ->
            computeNext(params, dependEntity)
        }
    }
}