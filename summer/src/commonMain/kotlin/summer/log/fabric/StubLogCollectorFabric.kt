package summer.log.fabric

import summer.log.StubLogCollector

class StubLogCollectorFabric : LogCollectorsFactory {
    override fun create(tag: String) = StubLogCollector
}