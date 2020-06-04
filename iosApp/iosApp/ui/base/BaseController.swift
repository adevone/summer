//
//  BaseController.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit
import shared

class BaseController: UIViewController {
    
    private var controller: BasePresenterController!
    
    func setPresenter(_ controller: BasePresenterController) {
        controller.setViewProviderUnsafe(unsafeGetView: { [weak self] in
            return self
        })
        self.controller = controller
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if controller == nil {
            fatalError("\(String(describing: self)) call setPresenter before super.viewDidLoad")
        }
        
        controller.viewCreated()
    }
    
    deinit {
        controller.onDestroy()
    }
}

class BaseTabBarController: UITabBarController {

    private var controller: BasePresenterController!
    
    func setPresenter(_ controller: BasePresenterController) {
        controller.setViewProviderUnsafe(unsafeGetView: { [weak self] in
            return self
        })
        self.controller = controller
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if controller == nil {
            fatalError("\(String(describing: self)) call setPresenter before super.viewDidLoad")
        }
        
        controller.viewCreated()
    }
    
    deinit {
        controller.onDestroy()
    }
}
