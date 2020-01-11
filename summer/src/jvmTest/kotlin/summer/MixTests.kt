package summer

import kotlinx.coroutines.Dispatchers
import summer.execution.SummerExecutor
import summer.execution.mix.execute
import summer.execution.reducer.SummerReducer
import summer.execution.source.SummerSource
import kotlin.test.Test
import kotlin.test.assertEquals

class MixTests {

    @Test
    fun sourceExecuted() {

        val sourceValue = "test"
        val reducerValue = "test2"

        val source = object : SummerSource<String, Unit> {

            override suspend fun invoke(params: Unit): String {
                return sourceValue
            }
        }

        lateinit var actualResult: String

        val summerExecutor = object : SummerExecutor(Dispatchers.Unconfined, Dispatchers.Unconfined, loggersFactory) {

            val reducer = object : SummerReducer<String, Unit>(this) {

                override suspend fun initial(): String {
                    return reducerValue
                }

                override suspend fun next(params: Unit, previous: String): String {
                    throw NotImplementedError()
                }
            }

            val executor = source.mix(reducer) { a, b -> a + b }
                .executor(
                    onSuccess = { result, _ ->
                        actualResult = result
                    }
                )
        }

        val expectedValue = sourceValue + reducerValue

        summerExecutor.receiverCreated()
        summerExecutor.executor.execute()

        assertEquals(expectedValue, actualResult)
    }

    @Test
    fun reducerExecuted() {


        val sourceValue = "test"
        val reducerValue = "test2"

        val source = object : SummerSource<String, Unit> {

            override suspend fun invoke(params: Unit): String {
                return sourceValue
            }
        }

        lateinit var actualResult: String

        val summerExecutor = object : SummerExecutor(Dispatchers.Unconfined, Dispatchers.Unconfined, loggersFactory) {

            val reducer = object : SummerReducer<String, Unit>(this) {

                override suspend fun initial(): String {
                    return ""
                }

                override suspend fun next(params: Unit, previous: String): String {
                    return reducerValue
                }
            }

            val executor = source.mix(reducer) { a, b -> a + b }
                .executor(
                    onSuccess = { result, _ ->
                        actualResult = result
                    }
                )
        }

        val expectedValue = sourceValue + reducerValue

        summerExecutor.receiverCreated()
        summerExecutor.executor.execute()
        summerExecutor.reducer(Unit)

        assertEquals(expectedValue, actualResult)
    }
}