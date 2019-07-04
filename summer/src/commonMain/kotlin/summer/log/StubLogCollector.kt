package summer.log

object StubLogCollector : LogCollector {

    override fun info(any: Any) {
        // do nothing
    }

    override fun info(f: () -> String) {
        // do nothing
    }

    override fun error(f: () -> String) {
        // do nothing
    }

    override fun error(e: Throwable) {
        // do nothing
    }

    override fun debug(f: () -> String) {
        // do nothing
    }

}