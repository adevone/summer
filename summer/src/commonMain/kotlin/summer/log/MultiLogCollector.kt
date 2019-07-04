package summer.log

class MultiLogCollector(
    private val collectors: Array<LogCollector>
) : LogCollector {

    override fun info(any: Any) = collectors.forEach { it.info(any) }

    override fun info(f: () -> String) = collectors.forEach { it.info(f) }

    override fun error(f: () -> String) = collectors.forEach { it.error(f) }

    override fun error(e: Throwable) = collectors.forEach { it.error(e) }

    override fun debug(f: () -> String) = collectors.forEach { it.debug(f) }

}