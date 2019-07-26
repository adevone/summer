package summer

interface SummerSource<out TEntity, in TParams> {
    suspend operator fun invoke(params: TParams): TEntity

    companion object {
        suspend operator fun <TEntity> SummerSource<TEntity, Unit>.invoke(): TEntity = this.invoke(Unit)
        suspend operator fun <TEntity> SummerSharedSource<TEntity, Unit>.invoke() = this.invoke(Unit)
    }
}

suspend operator fun <TEntity> SummerSource<TEntity, Unit>.invoke() = invoke(Unit)