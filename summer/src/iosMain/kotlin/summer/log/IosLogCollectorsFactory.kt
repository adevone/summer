package summer.log

import summer.log.fabric.LogCollectorsFactory

object IosLogCollectorsFactory : LogCollectorsFactory {

    override fun create(tag: String): LogCollector =
        IosLogCollector(tag)

    private class IosLogCollector(
        private val tag: String
    ) : LogCollector {

        override fun debug(f: () -> String) {
            println("$tag: ${f()}")
        }

        override fun error(f: () -> String) {
            println("$tag: ${f()}")
        }

        private val spacesPattern = "\\s+".toRegex()
        override fun error(e: Throwable) {
            val message = buildString {
                append(e::class.qualifiedName)
                append(": ")
                append(e.message)
                append("\n")
                e.getStackTrace().forEach { stackTraceEntry ->
                    append("        at ")
                    val sourceFileEntry = stackTraceEntry
                        .substringAfter("(/opt/teamcity-agent/work")
                        .substringAfter("/src/")
                        .let {
                            if (it.startsWith("main/"))
                                it.substringAfter("main/")
                            else
                                it
                        }
                        .replace("kotlin/kotlin", "kotlin")
                        .replace("commonMain/kotlin/", "")
                        .replace(" + ", ":")
                    val sourceCallEntry = stackTraceEntry.substringBefore("(/opt/teamcity-agent/work").trim()
                    val deduplicatedSpacesCallEntry = sourceCallEntry
                        .replace(spacesPattern, " ")
                        .replace("kfun:", "")
                    var droopedSpacesCount = 0
                    val callEntry = deduplicatedSpacesCallEntry
                        .dropWhile {
                            if (it == ' ') droopedSpacesCount++
                            droopedSpacesCount < 3
                        }
                        .substringBefore("(")
                        .substringBefore("+")
                        .trim()
                    append("$callEntry ($sourceFileEntry")
                    append("\n")
                }
            }
            println("$tag: $message")
        }

        override fun info(f: () -> String) {
            println("$tag: ${f()}")
        }

        override fun info(any: Any) {
            println(any)
        }
    }
}
