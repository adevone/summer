@file:JvmName("LogForJvm")

package summer.log

import kotlin.reflect.KClass

actual var logCollectorsProvider = LogCollectorsProvider(JvmLogCollectorsFactory)

actual fun tagByNestedClass(clazz: KClass<*>): String = clazz.java.name
    .substringAfterLast("src/jvmMain/java")
    .substringBefore("$")

actual fun tagByClass(clazz: KClass<*>): String = clazz.java.name
    .substringAfterLast(".")