package summer

import summer.store.InMemoryStore
import summer.store.SummerStore
import kotlin.test.Test
import kotlin.test.assertEquals

class StoreTests {

    @Test
    fun put() {

        val store: SummerStore = InMemoryStore()

        val expectedValue = "test"

        lateinit var actualValue: String
        var property by store.store(
            onSet = { value ->
                actualValue = value
            },
            initialValue = ""
        )

        property = expectedValue

        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue, property)
    }

    @Test
    fun restore() {

        val store: SummerStore = InMemoryStore()

        val expectedValue = "test"

        {
            @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
            var property by store.store(
                onSet = {},
                initialValue = ""
            )

            @Suppress("UNUSED_VALUE")
            property = expectedValue
        }() // to allow multiple variables with name "property"

        lateinit var actualValue: String
        val property by store.store(
            onSet = { value ->
                actualValue = value
            },
            initialValue = ""
        )

        store.restore()

        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue, property)
    }

    @Test
    fun restoreInitial() {

        val store: SummerStore = InMemoryStore()

        val expectedValue = "test"

        lateinit var actualValue: String

        val property by store.store(
            onSet = { value ->
                actualValue = value
            },
            initialValue = expectedValue
        )

        store.restore()

        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue, property)
    }
}