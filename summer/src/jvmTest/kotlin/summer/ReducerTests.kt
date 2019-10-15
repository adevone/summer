package summer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import summer.execution.reducer.SummerReducer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReducerTests {

    @Test
    fun initial(): Unit = runBlocking {

        val initialValue = "test"

        val scope = GlobalScope
        val reducer = object : SummerReducer<String, Unit>(scope) {

            override suspend fun initial(): String {
                return initialValue
            }

            override suspend fun next(params: Unit, previous: String): String {
                throw NotImplementedError()
            }
        }

        val value = reducer.get()

        assertEquals(initialValue, value)
    }

    @Test
    fun notDependentNext(): Unit = runBlocking {

        val expectedValue = "test"

        val scope = CoroutineScope(Dispatchers.Unconfined)
        val reducer = object : SummerReducer<String, Unit>(scope) {

            override suspend fun initial(): String {
                return ""
            }

            override suspend fun next(params: Unit, previous: String): String {
                return expectedValue
            }
        }

        reducer(Unit)
        val value = reducer.get()

        assertEquals(expectedValue, value)
    }

    @Test
    fun dependentNext(): Unit = runBlocking {

        val initialValue = "test"
        val nextValue = "test2"

        val scope = CoroutineScope(Dispatchers.Unconfined)
        val reducer = object : SummerReducer<String, Unit>(scope) {

            override suspend fun initial(): String {
                return initialValue
            }

            override suspend fun next(params: Unit, previous: String): String {
                return previous + nextValue
            }
        }

        val expectedValue = initialValue + nextValue

        reducer(Unit)
        val value = reducer.get()

        assertEquals(expectedValue, value)
    }

    @Test
    fun dependOn() = runBlocking {

        val scope = CoroutineScope(Dispatchers.Unconfined)
        val sourceReducer = object : SummerReducer<Unit, Unit>(scope) {

            override suspend fun initial() {
                // do nothing
            }

            override suspend fun next(params: Unit, previous: Unit) {
                // do nothing
            }
        }

        var isNextCalled = false
        object : SummerReducer<Unit, Unit>(scope) {

            init {
                depend(sourceReducer) { _, _, _ ->
                    this(Unit)
                }
            }

            override suspend fun initial() {
                // do nothing
            }

            override suspend fun next(params: Unit, previous: Unit) {
                isNextCalled = true
            }
        }

        sourceReducer(Unit)

        assertTrue(isNextCalled)
    }
}