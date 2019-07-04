package summer.log

interface LogCollector {
    fun error(e: Throwable)
    fun info(any: Any)
    fun info(f: () -> String)
    fun error(f: () -> String)
    fun debug(f: () -> String)
}