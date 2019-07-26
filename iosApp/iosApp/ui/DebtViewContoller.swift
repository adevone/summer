//
//  MainViewController.swift
//  iosApp
//
//  Created by adev on 24/05/2019.
//

import UIKit
import shared

extension DebtViewContoller: DebtRouter {
    
}

class DebtViewContoller: UIViewController, DebtViewState, DebtViewMethods  {
    
    private let presenter = DebtPresenter()
    
    @IBOutlet weak var debtView: UILabel!
    @IBOutlet weak var loanField: UITextField!
    
    var debt: Debt? = nil {
        didSet {
            debtView.text = "Долг: \(debt?.money ?? 0.0)"
        }
    }
    
    @IBAction func onCalculateDebtTap(_ sender: Any) {
        let loan = Float(loanField.text ?? "") ?? 0.0
        presenter.onCalculateDebtClick(loan: loan)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        presenter.onCreateView(viewState: self, viewMethods: self, router: self, owner: self)
    }
}
