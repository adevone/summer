//
//  MainView.swift
//  SummerSwiftUI
//
//  Created by Kirill Terekhov on 02.01.2021.
//

import SwiftUI
import shared

class MainViewState: BaseState, MainView {
    @Published var selectedTab: Tab? = nil
    @Published var tabs = [Tab]()
}

struct MainUI: View {

    @ObservedObject var state = MainViewState()
    var viewModel = MainViewModel()
    init() {
        state.bind(viewModel)
    }

    var body: some View {
        TabView {
            ForEach(state.tabs, id: \.self) { tab in
                switch tab {
                case .frameworks:
                    FrameworksUI().tabItem {
                        Image(uiImage: UIImage(named: "frameworks_icon")!)
                        Text("Frameworks")
                    }
                case .about:
                    AboutUI().tabItem {
                        Image(uiImage: UIImage(named: "about_icon")!)
                        Text("About")
                    }
                case .basket:
                    BasketUI().tabItem {
                        Image(uiImage: UIImage(named: "basket_icon")!)
                        Text("Basket")
                    }
                default:
                    fatalError()
                }
            }
        }
    }
}

struct MainUI_Previews: PreviewProvider {
    static var previews: some View {
        MainUI()
    }
}
