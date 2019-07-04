package summer.log

import kotlin.reflect.KClass

actual var logCollectorsProvider = LogCollectorsProvider(JsLogCollectorsFactory)

actual fun tagByNestedClass(clazz: KClass<*>) = "#KT"

actual fun tagByClass(clazz: KClass<*>) = "#KT"