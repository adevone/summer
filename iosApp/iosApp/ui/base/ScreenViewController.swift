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
    
    open override func viewDidLoad() {
        super.viewDidLoad()
    }

}

