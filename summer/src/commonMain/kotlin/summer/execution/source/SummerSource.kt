package summer.execution.source

import summer.execution.reducer.SummerReducer

interface SummerSource<out TEntity, in TParams> {
    suspend operator fun invoke(params: TParams): TEntity

    companion object {
        suspend operator fun <TEntity> SummerSource<TEntity, Unit>.invoke(): TEntity = this.invoke(Unit)
        operator fun <TEntity> SummerReducer<TEntity, Unit>.invoke() = this.invoke(Unit)
    }
}

suspend operator fun <TEntity> SummerSource<TEntity, Unit>.invoke() = invoke(Unit)