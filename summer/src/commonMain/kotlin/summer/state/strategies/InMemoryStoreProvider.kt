package summer.state.strategies

/**
 * Owner of [InMemoryStrategy]
 */
interface InMemoryStoreProvider {
    val inMemoryStore: InMemoryStore
}