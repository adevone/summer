package summer.android.bundle

import android.os.Bundle
import summer.state.StateDelegate

interface BundleProvider {
    val bundle: Bundle
}

typealias BundleStateDelegateProvider<T> = StateDelegate.Provider<T, BundleProvider>