//
//  BaseController.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit
import shared

class BaseController: UIViewController, NavigationView {
    
    private var controller: BaseViewModelController!
    
    func setViewModel(_ controller: BaseViewModelController) {
        controller.setViewProviderUnsafe(unsafeGetView: { [weak self] in
            return self
        })
        self.controller = controller
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if controller == nil {
            fatalError("\(String(describing: self)) call setViewModel before super.viewDidLoad")
        }
        
        controller.viewCreated()
    }

    override func viewDidAppear() {
        super.viewDidAppear()
        controller.viewAppeared()
    }

    override func viewWillDisappear() {
        super.viewWillDisappear()
        controller.viewDisappeared()
    }

    deinit {
        controller.onDestroy()
    }

    lazy var navigate: (@escaping (AppNavigator) -> KotlinUnit) -> Void = { navigation in
        // do navigation
    }
}

class BaseTabBarController: UITabBarController {

    private var controller: BaseViewModelController!
    
    func setViewModel(_ controller: BaseViewModelController) {
        controller.setViewProviderUnsafe(unsafeGetView: { [weak self] in
            return self
        })
        self.controller = controller
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if controller == nil {
            fatalError("\(String(describing: self)) call setViewModel before super.viewDidLoad")
        }
        
        controller.viewCreated()
    }

    override func viewDidAppear() {
        super.viewDidAppear()
        controller.viewAppeared()
    }

    override func viewWillDisappear() {
        super.viewWillDisappear()
        controller.viewDisappeared()
    }
    
    deinit {
        controller.onDestroy()
    }
}
