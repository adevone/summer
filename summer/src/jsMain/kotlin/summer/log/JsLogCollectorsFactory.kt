package summer.log

import summer.log.fabric.LogCollectorsFactory

object JsLogCollectorsFactory : LogCollectorsFactory {

    override fun create(tag: String): LogCollector =
        JavaLogCollector(tag)

    private class JavaLogCollector(
        private val tag: String
    ) : LogCollector {

        override fun info(any: Any) {
            println(any)
        }

        override fun info(f: () -> String) {
            println("$tag: ${f()}")
        }

        override fun error(e: Throwable) {
            println(e)
        }

        override fun error(f: () -> String) {
            println("$tag: ${f()}")
        }

        override fun debug(f: () -> String) {
            println("$tag: ${f()}")
        }
    }
}
