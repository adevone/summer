package summer.example.presentation

import summer.example.entity.Tab
import summer.example.presentation.base.ScreenPresenter

object MainView {

    interface State {
        var tabs: List<Tab>
        var selectedTab: Tab?
    }

    interface Methods {

    }
}

interface MainRouter {

}

class MainPresenter : ScreenPresenter<
        MainView.State,
        MainView.Methods,
        MainRouter>() {

    private val allTabs = Tab.values().toList()

    override val viewStateProxy = object : MainView.State {
        override var tabs by store({ it::tabs }, initial = allTabs)
        override var selectedTab by store({ it::selectedTab }, initial = allTabs.first())
    }

    fun onMenuItemClick(tab: Tab) {
        viewStateProxy.selectedTab = tab
    }
}