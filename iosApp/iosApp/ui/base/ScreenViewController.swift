//
//  ScreenViewController.swift
//  iosApp
//
//  Created by adev on 05/07/2019.
//

import UIKit
import shared

open class ScreenViewController<TViewState, TViewMethods, TRouter, TPresenter: ScreenPresenter<TViewState, TViewMethods, TRouter>>: UIViewController {

    open func createPresenter() -> TPresenter { fatalError("define presenter pls") }
    
    var presenter: TPresenter!
    
    open override func viewDidLoad() {
        super.viewDidLoad()
        presenter = createPresenter()
        presenter.viewCreated(viewState: self as! TViewState, viewMethods: self as! TViewMethods)
        presenter.routerCreated(router: self as! TRouter)
    }

    open override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        presenter.appeared()
    }
    
    open override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        presenter.disappeared()
    }
}

