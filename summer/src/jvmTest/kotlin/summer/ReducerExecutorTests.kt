package summer

import kotlinx.coroutines.Dispatchers
import summer.execution.SummerExecutor
import summer.execution.reducer.SummerReducer
import java.lang.RuntimeException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ReducerExecutorTests {

    @Test
    fun success() {

        val initialValue = "test"

        lateinit var actualResult: String

        val summerExecutor = object : SummerExecutor(Dispatchers.Unconfined, Dispatchers.Unconfined, loggersFactory) {

            val reducer = object : SummerReducer<String, Unit>(this) {

                override suspend fun initial(): String {
                    return initialValue
                }

                override suspend fun next(params: Unit, previous: String): String {
                    throw NotImplementedError()
                }
            }

            init {
                reducer.executor(
                    onSuccess = { result, _ ->
                        actualResult = result
                    }
                )
            }
        }

        summerExecutor.receiverCreated()

        assertEquals(initialValue, actualResult)
    }

    @Test
    fun fail() {

        lateinit var throwable: Throwable

        val summerExecutor = object : SummerExecutor(Dispatchers.Unconfined, Dispatchers.Unconfined, loggersFactory) {

            val reducer = object : SummerReducer<String, Unit>(this) {

                override suspend fun initial(): String {
                    throw RuntimeException("fail!")
                }

                override suspend fun next(params: Unit, previous: String): String {
                    throw NotImplementedError()
                }
            }

            init {
                reducer.executor(
                    onFailure = { e, _ ->
                        throwable = e
                    }
                )
            }
        }

        summerExecutor.receiverCreated()

        assertNotNull(throwable)
    }
}