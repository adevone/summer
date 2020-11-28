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

class AboutController: BaseController, AboutView {
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var authorNameLabel: UILabel!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
    
    var about: About? = nil {
        didSet {
            titleLabel.text = about?.frameworkName ?? ""
            authorNameLabel.text = about?.author ?? ""
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
            activityIndicator.isHidden = !isLoading
        }
    }
    
    private var viewModel: AboutViewModel! {
        didSet { setViewModel(viewModel) }
    }
    
    override func viewDidLoad() {
        viewModel = AboutViewModel()
        super.viewDidLoad()
    }
    
}
