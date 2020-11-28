//
//  BaseController.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit
import shared

class BaseController: UIViewController {
    
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
    
    deinit {
        controller.onDestroy()
    }
}

class BaseTabBarController: UITabBarController {

    private var controller: SummerViewModelController!
    
    func setViewModel(_ controller: SummerViewModelController) {
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
    
    deinit {
        controller.onDestroy()
    }
}
