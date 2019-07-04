package summer.log

import summer.log.fabric.LogCollectorsFactory
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty

open class LogCollectorsProvider(
    private val defaultLogCollectorsFactory: LogCollectorsFactory
) {

    companion object {
        protected fun benchmark(className: String, methodName: String) = "B/$className/$methodName"
    }

    protected open val logCollectorFactories: Map<String, LogCollectorsFactory> = emptyMap()

    private val logCollectors: Map<String, LogCollector> by lazy {
        logCollectorFactories
            .map { (tag, fabric) ->
                tag to fabric.create(tag)
            }
            .toMap()
    }

    fun create(f: KFunction<*>) =
        create(f.name)

    fun create(p: KProperty<*>) =
        create(p.name)

    fun create(tag: String): LogCollector =
        logCollectors[tag] ?: defaultLogCollectorsFactory.create(tag)

}