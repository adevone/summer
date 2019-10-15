package summer.execution.source

import summer.execution.reducer.SummerReducer

/**
 * Base class for interactors.
 * Allows [SourceExecutor] to manage multithreading and lifecycle of your business logic
 *
 * Should not be user as direct parent of interactors.
 * You should create your own base class or interface that implement [SummerSource] in your project
 */
interface SummerSource<out TEntity, in TParams> {

    /**
     * Trigger interactor
     */
    suspend operator fun invoke(params: TParams): TEntity

    companion object {
        suspend operator fun <TEntity> SummerSource<TEntity, Unit>.invoke(): TEntity = this.invoke(Unit)
        operator fun <TEntity> SummerReducer<TEntity, Unit>.invoke() = this.invoke(Unit)
    }
}

suspend operator fun <TEntity> SummerSource<TEntity, Unit>.invoke() = invoke(Unit)