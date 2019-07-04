package summer.log

import android.util.Log
import summer.log.fabric.LogCollectorsFactory

object AndroidLogCollectorsFactory : LogCollectorsFactory {

    override fun create(tag: String): LogCollector =
        ConsoleLogCollector(tag)

}

private class ConsoleLogCollector(
    private val tag: String
) : LogCollector {

    override fun info(any: Any) {
        Log.i(tag, any.toString())
    }

    override fun info(f: () -> String) {
        Log.i(tag, f())
    }

    override fun error(e: Throwable) {
        Log.e(tag, Log.getStackTraceString(e))
    }

    override fun error(f: () -> String) {
        Log.e(tag, f())
    }

    override fun debug(f: () -> String) {
        Log.d(tag, f())
    }

}
