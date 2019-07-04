package summer

interface SummerSource<out TEntity, in TParams> {
    suspend fun execute(params: TParams): TEntity
}

suspend fun <TEntity> SummerSource<TEntity, Unit>.execute() = execute(Unit)