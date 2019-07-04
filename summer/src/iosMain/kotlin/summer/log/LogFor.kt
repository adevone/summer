package summer.log

import kotlin.reflect.KClass

actual var logCollectorsProvider = LogCollectorsProvider(IosLogCollectorsFactory)

actual fun tagByNestedClass(clazz: KClass<*>) = clazz.qualifiedName
    ?.split(".")
    ?.takeLast(2)
    ?.first() ?: "#KT"

actual fun tagByClass(clazz: KClass<*>) = clazz.qualifiedName
    ?.split(".")
    ?.lastOrNull() ?: "#KT"