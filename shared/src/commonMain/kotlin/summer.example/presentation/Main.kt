package summer.example.presentation

import summer.example.entity.Tab
import summer.example.presentation.base.BaseViewModel

interface MainView {
    var tabs: List<Tab>
    var selectedTab: Tab?
}

class MainViewModel : BaseViewModel<MainView>() {

    private val allTabs = Tab.values().toList()
    override val viewProxy = object : MainView {
        override var tabs by state({ it::tabs }, initial = allTabs)
        override var selectedTab by state({ it::selectedTab }, initial = allTabs.first())
    }

    fun onMenuItemClick(tab: Tab) {
        viewProxy.selectedTab = tab
    }
}