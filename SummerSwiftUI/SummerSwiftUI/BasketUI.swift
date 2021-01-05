//
//  BasketUI.swift
//  SummerSwiftUI
//
//  Created by Kirill Terekhov on 02.01.2021.
//

import SwiftUI
import shared

class BasketViewState: BaseState, BasketView {
    @Published var items = [Basket.Item]()
}

struct BasketUI: View {

    @ObservedObject var state = BasketViewState()
    var viewModel = ServiceLocator().basketViewModel()
    init() {
        state.bind(viewModel)
    }

    var body: some View {
        VStack {
            ForEach(state.items, id: \.self) { item in
                HStack {
                    Text(item.framework.name).padding()
                    Spacer()
                    Text("\(item.quantity)").padding()
                }
            }
        }
    }
}

struct BasketUI_Previews: PreviewProvider {
    static var previews: some View {
        BasketUI()
    }
}
