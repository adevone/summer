//
//  BaseController.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit
import shared

class BaseController: UIViewController {
    
    private var lifecycleOwner: SummerUnsafePresenterLifecycleOwnerWithRouter!
    
    func setPresenter(_ lifecycleOwner: SummerUnsafePresenterLifecycleOwnerWithRouter) {
        self.lifecycleOwner = lifecycleOwner
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if lifecycleOwner == nil {
            fatalError("\(String(describing: self)) call setPresenter before super.viewDidLoad")
        }
        
        lifecycleOwner.viewCreatedUnsafe(viewState: self, viewMethods: self)
        lifecycleOwner.routerCreatedUnsafe(router: self)
        
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
        lifecycleOwner.routerDestroyed()
        lifecycleOwner.destroyed()
    }
}

class BaseTabBarController: UITabBarController {

    private var lifecycleOwner: SummerUnsafePresenterLifecycleOwnerWithRouter!
    
    func setPresenter(_ lifecycleOwner: SummerUnsafePresenterLifecycleOwnerWithRouter) {
        self.lifecycleOwner = lifecycleOwner
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        lifecycleOwner.viewCreatedUnsafe(viewState: self, viewMethods: self)
        lifecycleOwner.routerCreatedUnsafe(router: self)
        
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
        lifecycleOwner.routerDestroyed()
        lifecycleOwner.destroyed()
    }
}
