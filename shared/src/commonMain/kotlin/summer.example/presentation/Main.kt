package summer.example.presentation

import kotlinx.serialization.Serializable
import summer.example.entity.Tab
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.exhaustive

interface MainView {
    var tabs: List<Tab>
    var selectedTab: Tab?
}

sealed class MainInput {

    @Serializable
    data class MenuItemClicked(val tab: Tab) : MainInput()
}

class MainViewModel : BaseViewModel<MainView, MainInput>() {

    private val allTabs = Tab.values().toList()
    override val viewProxy = object : MainView {
        override var tabs by state({ it::tabs }, initial = allTabs)
        override var selectedTab by state({ it::selectedTab }, initial = allTabs.first())
    }

    override fun handle(input: MainInput) {
        when (input) {
            is MainInput.MenuItemClicked -> {
                viewProxy.selectedTab = input.tab
            }
        }.exhaustive
    }
}