package summer.example.presentation

import summer.example.entity.Tab
import summer.example.presentation.base.ScreenPresenter

interface MainView {
    var tabs: List<Tab>
    var selectedTab: Tab?
}

class MainPresenter : ScreenPresenter<MainView>() {

    private val allTabs = Tab.values().toList()

    override val viewProxy = object : MainView {
        override var tabs by state({ it::tabs }, initial = allTabs)
        override var selectedTab by state({ it::selectedTab }, initial = allTabs.first())
    }

    fun onMenuItemClick(tab: Tab) {
        viewProxy.selectedTab = tab
    }
}