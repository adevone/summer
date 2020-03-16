//
//  AboutController.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit
import shared
import Alamofire
import AlamofireImage

extension AboutController: AboutViewMethods {
    
}

extension AboutController: AboutRouter {
    
    
}

class AboutController: BaseController, AboutViewState {
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var authorNameLabel: UILabel!
    @IBOutlet weak var imageView: UIImageView!
    
    var about: About? = nil {
        didSet {
            titleLabel.text = about?.frameworkName ?? "Загрузка..."
            authorNameLabel.text = about?.author ?? "Загрузка..."
            if let logoUrl = about?.logoUrl {
                AF.request(logoUrl).responseImage { [weak self] response in
                    if case .success(let image) = response.result {
                        self?.imageView.image = image
                    }
                }
            }
        }
    }
    
    var isLoading: Bool = false {
        didSet {
            print(isLoading)
        }
    }
    
    var doSomething: (String) -> Void = { smth in
        
    }
    
    private var presenter: AboutPresenter! {
        didSet { setPresenter(presenter) }
    }
    
    override func viewDidLoad() {
        presenter = AboutPresenter()
        super.viewDidLoad()
    }
    
}
