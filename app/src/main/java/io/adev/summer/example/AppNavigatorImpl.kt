package io.adev.summer.example

import androidx.fragment.app.FragmentManager
import io.adev.summer.example.entity.Framework
import io.adev.summer.example.ui.frameworks.FrameworkDetailsFragment

interface NavigationHost {
    val containerId: Int
    val fragmentManager: FragmentManager
}

class AppNavigatorImpl(
    private val host: NavigationHost
) : AppNavigator {

    override fun toFrameworkDetails(framework: Framework) {
        host.fragmentManager.beginTransaction()
            .replace(host.containerId, FrameworkDetailsFragment.newInstance(framework))
            .addToBackStack(FrameworkDetailsFragment::class.simpleName)
            .commit()
    }
}