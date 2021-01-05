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
    
    private var viewModel: FrameworksViewModel! {
        didSet { setViewModel(viewModel) }
    }
    
    override func viewDidLoad() {
        viewModel = FrameworksViewModel()
        super.viewDidLoad()
        frameworksTable.dataSource = self
        frameworksTable.delegate = self
    }
    
}

extension FrameworksController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let item = items[indexPath.row]
        viewModel.onItemClick(item: item)
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
        viewModel.onIncreaseClick(item: item)
    }
    
    @IBAction func decreaseTapped(_ sender: Any) {
        viewModel.onDecreaseClick(item: item)
    }
    
    private var item: Basket.Item!
    private var viewModel: FrameworksViewModel!
    func setup(_ item: Basket.Item, _ viewModel: FrameworksViewModel) {
        self.item = item
        self.viewModel = viewModel
        titleLabel.text = "\(item.framework.name) (\(item.quantity))"
    }
}
