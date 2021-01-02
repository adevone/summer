//
//  BaseState.swift
//  SummerSwiftUI
//
//  Created by Kirill Terekhov on 03.01.2021.
//

import shared

typealias BaseState = ObservableObject & SummerBinder

class SummerBinder {
    var controller: BaseViewModelController? = nil

    func bind(_ controller: BaseViewModelController) {
        controller.setViewProviderUnsafe(unsafeGetView: { [weak self] in
            return self
        })
        controller.viewCreated()
        self.controller = controller
    }

    deinit {
        controller?.onDestroy()
    }
}
