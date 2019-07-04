package summer.log

import summer.log.fabric.LogCollectorsFactory

object JvmLogCollectorsFactory : LogCollectorsFactory {

    override fun create(tag: String): LogCollector =
        JavaLogCollector(tag)

    private class JavaLogCollector(
        private val tag: String
    ) : LogCollector {

        override fun info(any: Any) {
            System.out.println(any)
        }

        override fun info(f: () -> String) {
            System.out.println("$tag: ${f()}")
        }

        override fun error(e: Throwable) {
            e.printStackTrace(System.err)
        }

        override fun error(f: () -> String) {
            System.err.println("$tag: ${f()}")
        }

        override fun debug(f: () -> String) {
            System.out.println("$tag: ${f()}")
        }

    }

}
