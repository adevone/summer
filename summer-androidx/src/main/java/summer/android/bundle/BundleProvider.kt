package summer.android.bundle

import android.os.Bundle
import summer.state.SummerStateDelegate

interface BundleProvider {
    val bundle: Bundle
}

typealias BundleStateDelegateProvider<T> = SummerStateDelegate.Provider<T, BundleProvider>