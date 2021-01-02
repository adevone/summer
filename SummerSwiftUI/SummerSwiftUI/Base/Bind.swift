//
//  Mvvm.swift
//  SummerSwiftUI
//
//  Created by Kirill Terekhov on 02.01.2021.
//

import SwiftUI
import shared

class Bind {
    let controller: BaseViewModelController
    let state: AnyObject
    var isBound = false
    init(
        _ controller: BaseViewModelController,
        _ state: AnyObject
    ) {
        self.controller = controller
        self.state = state
    }
}

protocol BindView: View {
    associatedtype Content: View
    var contentView: Self.Content { get }
    var bind: Bind { get }
}

extension BindView {

    var body: some View {
        if !bind.isBound {
            bind(bind.controller, bind.state)
            bind.isBound = true
        }
        return contentView
    }
}

extension View {

    func bind(
        _ controller: BaseViewModelController,
        _ state: AnyObject
    ) {
        weak var weakState = state
        controller.setViewProviderUnsafe(unsafeGetView: {
            return weakState
        })
        controller.viewCreated()
    }
}
