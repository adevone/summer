package summer

import kotlin.coroutines.CoroutineContext

expect val defaultUiContext: CoroutineContext
expect val defaultBackgroundContext: CoroutineContext

expect object DefaultLoggerFactory : SummerLogger.Factory