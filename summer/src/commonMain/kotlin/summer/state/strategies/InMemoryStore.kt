package summer.state.strategies

import summer.SummerViewModel

/**
 * Default store for [SummerViewModel].
 * Saved values will be restored only when they saved in same instance.
 */
class InMemoryStore {
    private var isInitByKey = mutableMapOf<String, Unit>()
    private val storedValuesByKey = mutableMapOf<String, Any?>()

    fun isInit(key: String): Boolean {
        return key in isInitByKey
    }

    fun <T> get(key: String): T {
        @Suppress("UNCHECKED_CAST")
        return storedValuesByKey[key] as T
    }

    fun <T> set(key: String, value: T) {
        storedValuesByKey[key] = value
        isInitByKey[key] = Unit
    }
}