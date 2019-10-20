package summer.example.domain

import kotlinx.coroutines.CoroutineScope
import summer.execution.source.SummerSource
import summer.execution.reducer.SummerReducer

typealias UseCase<TEntity, TParams> = SummerSource<TEntity, TParams>

typealias SharedUseCase<TEntity, TParams> = SummerReducer<TEntity, TParams>

abstract class Reducer<TEntity, TParams>(
    scope: CoroutineScope
) : SummerReducer<TEntity, TParams>(
    scope
)