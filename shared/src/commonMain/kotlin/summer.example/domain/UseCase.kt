package summer.example.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import summer.SummerReducer
import summer.SummerSource
import kotlin.coroutines.CoroutineContext

typealias UseCase<TEntity, TParams> = SummerSource<TEntity, TParams>

abstract class SharedUseCase<TEntity, TParams>(
    dependencies: Dependencies
) : SummerReducer<TEntity, TParams>(
    CoroutineScope(dependencies.workContext + SupervisorJob())
) {
    class Dependencies(
        val workContext: CoroutineContext
    )
}

abstract class Reducer<TEntity, TParams>(
    scope: CoroutineScope
) : SummerReducer<TEntity, TParams>(
    scope
)