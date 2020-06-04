//
//  ContentView.swift
//  swiftUiApp
//
//  Created by Kirill Terekhov on 27.05.2020.
//  Copyright © 2020 Kirill Terekhov. All rights reserved.
//

import SwiftUI
import shared

class ContentViewState: ObservableObject, AboutView {
    @Published var about: About? = nil
    @Published var isLoading: Bool = false
}

struct ContentView: MvpView {

    @ObservedObject var state = ContentViewState()
    var presenter = AboutPresenter()

    var contentView: some View {
        Text(
            state.isLoading ? "true" : "false"
        )
    }

    var mvp: Mvp {
        Mvp(presenter, state)
    }
}













struct Mvp {
    let controller: BasePresenterController
    let state: AnyObject
    init(
        _ controller: BasePresenterController,
        _ state: AnyObject
    ) {
        self.controller = controller
        self.state = state
    }
}

protocol MvpView: View {
    associatedtype Content: View
    var contentView: Self.Content { get }
    var mvp: Mvp { get }
}

extension MvpView {

    var body: some View {
        contentView
    }
}

extension View {

    func bind(
        _ controller: BasePresenterController,
        _ state: AnyObject
    ) {
        weak var weakState = state
        controller.setViewProviderUnsafe(unsafeGetView: {
            return weakState
        })
        controller.viewCreated()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
