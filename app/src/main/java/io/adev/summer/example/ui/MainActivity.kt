package io.adev.summer.example.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.os.postDelayed
import io.adev.summer.example.R
import io.adev.summer.example.bindViewModel
import io.adev.summer.example.databinding.ActivityMainBinding
import io.adev.summer.example.entity.Tab
import io.adev.summer.example.presentation.MainView
import io.adev.summer.example.presentation.MainViewModel
import io.adev.summer.example.ui.base.BaseActivity
import io.adev.summer.example.ui.base.routing.BackButtonListener
import io.adev.summer.example.ui.base.routing.TabContainerFragment
import io.adev.summer.example.ui.base.routing.toScreen

class MainActivity : BaseActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = bindViewModel(MainViewModel::class, activity = this) { this }
    }

    override var tabs: List<Tab> by didSet {
        binding.bottomNavigationView.menu.clear()
        tabs.forEach { tab ->
            binding.bottomNavigationView.menu
                .add(
                    Menu.NONE,
                    tab.itemId,
                    Menu.NONE,
                    tab.title
                )
                .setIcon(tab.iconRes)
                .setOnMenuItemClickListener {
                    viewModel.onMenuItemClick(tab)
                    false
                }
        }
    }

    override var selectedTab: Tab? = null
        set(selectedTab) {
            val previousSelectedTab = field
            field = selectedTab
            if (selectedTab != null) {
                val currentFragment = supportFragmentManager.fragments.find { it.isVisible }
                val newFragment = supportFragmentManager.findFragmentByTag(selectedTab.name)

                if (currentFragment != null &&
                    newFragment != null &&
                    currentFragment === newFragment
                ) {
                    return
                }

                val transaction = supportFragmentManager.beginTransaction()
                if (newFragment == null) {
                    transaction.add(
                        R.id.contentContainer,
                        TabContainerFragment.Args(selectedTab).toScreen().fragment,
                        selectedTab.name
                    )
                }

                if (currentFragment != null) {
                    transaction.hide(currentFragment)
                }

                if (newFragment != null) {
                    transaction.show(newFragment)
                }
                transaction.commitNow()

                if (selectedTab != previousSelectedTab) {
                    binding.bottomNavigationView.selectedItemId = selectedTab.itemId
                }
            }
        }

    private val Tab.itemId: Int
        @IdRes get() = when (this) {
            Tab.Frameworks -> R.id.frameworksItem
            Tab.About -> R.id.aboutItem
            Tab.Basket -> R.id.basketItem
        }

    private val Tab.iconRes: Int
        @DrawableRes get() = when (this) {
            Tab.Frameworks -> R.drawable.menu_frameworks
            Tab.About -> R.drawable.menu_about
            Tab.Basket -> R.drawable.menu_basket
        }

    private val Tab.title: String
        get() = when (this) {
            Tab.Frameworks -> getString(R.string.menu_frameworks)
            Tab.About -> getString(R.string.menu_about)
            Tab.Basket -> getString(R.string.menu_basket)
        }

    private val dropIsBackClickedFirstTimesHandler = Handler(Looper.getMainLooper())
    private var isBackClickedFirstTimes = false
    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments.find { it.isVisible }
        if (fragment is BackButtonListener && fragment.onBackPressed()) {
            return
        } else {
            if (!isBackClickedFirstTimes) {
                Toast.makeText(this, "Нажмите ещё раз чтобы выйти", Toast.LENGTH_LONG).show()
                isBackClickedFirstTimes = true
                dropIsBackClickedFirstTimesHandler.postDelayed(2000L) {
                    isBackClickedFirstTimes = false
                }
            } else {
                super.onBackPressed()
            }
        }
    }
}