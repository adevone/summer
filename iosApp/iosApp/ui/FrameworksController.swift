//
//  FrameworksController.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit
import shared

extension FrameworksController: FrameworksViewMethods {
    
}

extension FrameworksController: FrameworksRouter {
    
    func toDetails(framework: Framework) {
        
    }
    
}

class FrameworksController: BaseController, FrameworksViewState {
    
    var items: [Basket.Item] = [] {
        didSet {
            print(items)
        }
    }
    
    private var presenter: FrameworksPresenter! {
        didSet { setPresenter(presenter) }
    }
    
    override func viewDidLoad() {
        presenter = FrameworksPresenter()
        super.viewDidLoad()
    }
    
}
