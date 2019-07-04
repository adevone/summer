package summer.log

open class KLogging(enabled: Boolean = true) {

    @Suppress("LeakingThis")
    val logger: LogCollector? = if (enabled)
        logFor(tagByNestedClass(this::class))
    else
        null

    @Suppress("NOTHING_TO_INLINE")
    inline fun LogCollector?.error(e: Throwable) {
        this?.error(e)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun LogCollector?.info(noinline f: () -> String) {
        this?.info(f)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun LogCollector?.info(any: Any) {
        this?.info(any)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun LogCollector?.error(noinline f: () -> String) {
        this?.error(f)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun LogCollector?.debug(noinline f: () -> String) {
        this?.debug(f)
    }

}