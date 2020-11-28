//
//  FrameworksController.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit
import shared

class FrameworksController: BaseController, FrameworksView {
    
    @IBOutlet weak var frameworksTable: UITableView!
    
    var items: [Basket.Item] = [] {
        didSet {
            frameworksTable.reloadData()
        }
    }
    
    lazy var toDetails: (Framework) -> Void = { framework in
        self.frameworksTable.reloadData()
    }
    
    private var viewModel: FrameworksViewModel! {
        didSet { setViewModel(viewModel) }
    }
    
    override func viewDidLoad() {
        viewModel = FrameworksViewModel()
        super.viewDidLoad()
        frameworksTable.dataSource = self
    }
    
}

extension FrameworksController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: FrameworkCell = tableView.cellFor(indexPath)
        let item = items[indexPath.row]
        cell.setup(item, viewModel)
        return cell
    }
}

class FrameworkCell: UITableViewCell {
    
    @IBOutlet weak var titleLabel: UILabel!
    
    @IBAction func increaseTapped(_ sender: Any) {
        viewModel.onIncreaseClick(framework: item.framework)
    }
    
    @IBAction func decreaseTapped(_ sender: Any) {
        viewModel.onDecreaseClick(framework: item.framework)
    }
    
    private var item: Basket.Item!
    private var viewModel: FrameworksViewModel!
    func setup(_ item: Basket.Item, _ viewModel: FrameworksViewModel) {
        self.item = item
        self.viewModel = viewModel
        titleLabel.text = "\(item.framework.name) (\(item.quantity))"
    }
}
