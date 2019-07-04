package summer.log.fabric

import summer.log.MultiLogCollector

class MultiLogCollectorFabric(
    private val collectorFactories: Array<LogCollectorsFactory>
) : LogCollectorsFactory {

    override fun create(tag: String) =
        MultiLogCollector(collectorFactories.map { it.create(tag) }.toTypedArray())

}