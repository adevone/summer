package summer.example.domain

import summer.SummerSharedSource
import summer.SummerSource
import kotlin.coroutines.CoroutineContext

typealias UseCase<TEntity, TParams> = SummerSource<TEntity, TParams>

abstract class SharedUseCase<TEntity, TParams>(
    dependencies: Dependencies
) : SummerSharedSource<TEntity, TParams>(
    dependencies.workContext
) {
    class Dependencies(
        val workContext: CoroutineContext
    )
}