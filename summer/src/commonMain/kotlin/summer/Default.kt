package summer

import kotlin.coroutines.CoroutineContext

expect val defaultUiContext: CoroutineContext
expect val defaultWorkContext: CoroutineContext

expect object DefaultLoggerFactory : SummerLogger.Factory