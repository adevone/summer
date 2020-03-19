//
//  BaseController.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit
import shared

class BaseController: UIViewController {
    
    private var controller: SummerPresenterController!
    
    func setPresenter(_ controller: SummerPresenterController) {
        controller.setViewProviderUnsafe(viewProvider: { [weak self] in
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

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        controller.appeared()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        controller.disappeared()
    }
    
    deinit {
        controller.exited()
        controller.viewDestroyed()
        controller.destroyed()
    }
}

class BaseTabBarController: UITabBarController {

    private var lifecycleOwner: SummerUnsafePresenterLifecycleOwner!
    
    func setPresenter(_ lifecycleOwner: SummerUnsafePresenterLifecycleOwner) {
        self.lifecycleOwner = lifecycleOwner
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        lifecycleOwner.viewCreatedUnsafe(view: self)
        
        lifecycleOwner.created()
        lifecycleOwner.entered()
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        lifecycleOwner.appeared()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        lifecycleOwner.disappeared()
    }
    
    deinit {
        lifecycleOwner.exited()
        lifecycleOwner.viewDestroyed()
        lifecycleOwner.destroyed()
    }
}
