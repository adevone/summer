package io.adev.summer.example.entity

data class Basket(
    val items: List<Item>
) {
    data class Item(
        val framework: Framework,
        val quantity: Int
    )

    fun allAsItems(frameworks: List<Framework>): List<Item> {
        return frameworks.map { framework ->
            val quantity = items.find { it.framework == framework }?.quantity ?: 0
            Item(framework, quantity)
        }
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