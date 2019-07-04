package summer.log.fabric

import summer.log.LogCollector

interface LogCollectorsFactory {
    fun create(tag: String): LogCollector
}