package summer.example.entity

import kotlinx.serialization.Serializable

@Serializable
data class Basket(
    val items: List<Item>,
) {
    @Serializable
    data class Item(
        val framework: Framework,
        val quantity: Int,
    )

    fun allAsItems(frameworks: List<Framework>): List<Item> {
        val items = items.filter { it.framework in frameworks }.toMutableList()
        val basketFrameworks = items.map { it.framework }
        frameworks.forEach { framework ->
            if (framework !in basketFrameworks) {
                items.add(Item(framework, quantity = 0))
            }
        }
        return items
    }

    fun withIncreased(framework: Framework): Basket {
        val currentItem = items.find { it.framework == framework }
        return if (currentItem != null) {
            Basket(
                items = items.map { item ->
                    if (item.framework == framework)
                        item.copy(quantity = item.quantity + 1)
                    else
                        item
                }
            )
        } else {
            Basket(
                items = items + Item(framework, quantity = 1)
            )
        }
    }

    fun withDecreased(framework: Framework): Basket {
        val currentItem = items.find { it.framework == framework }
        return when {
            currentItem == null -> {
                this
            }
            currentItem.quantity == 1 -> {
                Basket(items = items.filter { it.framework != framework })
            }
            else -> {
                Basket(
                    items = items.map { item ->
                        if (item.framework == framework)
                            item.copy(quantity = item.quantity - 1)
                        else
                            item
                    }
                )
            }
        }
    }
}