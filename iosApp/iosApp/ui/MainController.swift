//
//  MainViewController.swift
//  iosApp
//
//  Created by adev on 24/05/2019.
//

import UIKit
import shared

class MainController: BaseTabBarController, MainView {
    
    var selectedTab: Tab? = nil {
        didSet {
            
        }
    }
    
    var tabs: [Tab] = [] {
        didSet {
            viewControllers = tabs.compactMap({ tab in
                var controller: UIViewController? = nil
                var iconName: String? = nil
                var title: String? = nil
                if tab == Tab.frameworks {
                    let frameworksController: FrameworksController = storyboard!.controller()
                    iconName = "frameworks_icon"
                    title = "Фреймворки"
                    controller = frameworksController
                }
                if tab == Tab.about {
                    let aboutController: AboutController = storyboard!.controller()
                    iconName = "about_icon"
                    title = "О нас"
                    controller = aboutController
                }
                if tab == Tab.basket {
                    let basketController: BasketController = storyboard!.controller()
                    iconName = "basket_icon"
                    title = "Корзина"
                    controller = basketController
                }
                let tabBarItem = UITabBarItem(
                    title: title!,
                    image: UIImage(
                        named: iconName!
                    ),
                    tag: 0
                )
                controller!.tabBarItem = tabBarItem
                controller!.title = title!
                let baseNc = UINavigationController(rootViewController: controller!)
                return baseNc
            })
        }
    }
    
    private var presenter: MainPresenter! {
        didSet { setPresenter(presenter) }
    }
    
    override func viewDidLoad() {
        presenter = MainPresenter()
        super.viewDidLoad()
    }
}

extension UIStoryboard {

    func controller<Controller: UIViewController>() -> Controller {
        return instantiateViewController(
            withIdentifier: String(describing: Controller.self)
        ) as! Controller
    }
}
