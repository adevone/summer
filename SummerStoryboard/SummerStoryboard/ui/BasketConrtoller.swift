//
//  BasketConrtoller.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit
import shared

class BasketController: BaseController, BasketView {
    
    @IBOutlet weak var basketLabel: UILabel!
    
    var items: [Basket.Item] = [] {
        didSet {
            basketLabel.text = items
                .map { item in "\(item.framework.name)=\(item.quantity)" }
                .joined(separator: "\n")
            basketLabel.numberOfLines = items.count
        }
    }
    
    private var viewModel: BasketViewModel! {
        didSet { setViewModel(viewModel) }
    }
    
    override func viewDidLoad() {
        viewModel = ServiceLocator().basketViewModel()
        super.viewDidLoad()
    }
    
}
