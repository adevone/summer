//
//  BasketConrtoller.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit
import shared

extension BasketController: BasketViewMethods {
    
}

extension BasketController: BasketRouter {
    
}

class BasketController: BaseController, BasketViewState {
    
    var items: [Basket.Item] = [] {
        didSet {
            
        }
    }
    
    private var presenter: BasketPresenter! {
        didSet { setPresenter(presenter) }
    }
    
    override func viewDidLoad() {
        presenter = BasketPresenter()
        super.viewDidLoad()
    }
    
}
