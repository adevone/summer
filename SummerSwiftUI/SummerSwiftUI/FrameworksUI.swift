//
//  FrameworksUI.swift
//  SummerSwiftUI
//
//  Created by Kirill Terekhov on 02.01.2021.
//

import SwiftUI
import URLImage
import shared

class FrameworksViewState: BaseState, FrameworksView {
    @Published var items = [Basket.Item]()
    @Published var detailsFramework: Framework? = nil
    lazy var toDetails: (Framework) -> Void = { Framework in
        self.detailsFramework = nil
    }
}

struct FrameworksUI: View {

    @ObservedObject var state = FrameworksViewState()
    var viewModel = FrameworksViewModel()
    init() {
        state.bind(viewModel)
    }

    var body: some View {
        VStack {
            ForEach(state.items, id: \.self) { item in
                HStack {
                    VStack {
                        Text(item.framework.name)
                        Text(item.framework.version)
                    }.padding()
                    Spacer()
                    Button("-") {
                        viewModel.onDecreaseClick(item: item)
                    }.padding()
                    Text("\(item.quantity)")
                    Button("+") {
                        viewModel.onIncreaseClick(item: item)
                    }.padding()
                }
            }
        }
    }
}

struct FrameworksUI_Previews: PreviewProvider {
    static var previews: some View {
        FrameworksUI()
    }
}

