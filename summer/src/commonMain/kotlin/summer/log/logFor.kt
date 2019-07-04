package summer.log

import summer.log.fabric.StubLogCollectorFabric
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty

object StubLogCollectorsProvider : LogCollectorsProvider(StubLogCollectorFabric())

expect var logCollectorsProvider: LogCollectorsProvider

fun logFor(tag: String): LogCollector = logCollectorsProvider.create(tag)

fun logFor(clazz: KClass<*>): LogCollector = logCollectorsProvider.create(tagByClass(clazz))

fun logFor(f: KFunction<*>) = logCollectorsProvider.create(f.name)

fun logFor(p: KProperty<*>) = logCollectorsProvider.create(p.name)

expect fun tagByNestedClass(clazz: KClass<*>): String

expect fun tagByClass(clazz: KClass<*>): String