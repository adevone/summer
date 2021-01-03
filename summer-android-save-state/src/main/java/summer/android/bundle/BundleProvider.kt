package summer.android.bundle

import android.os.Bundle
import summer.state.StateProxy

interface BundleProvider {
    val bundle: Bundle
}

typealias BundleStateProxyProvider<T, TView> = StateProxy.Provider<T, TView, BundleProvider>