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
    
    lazy var toDetails: (Framework) -> Void = { [weak self] framework in
        self?.frameworksTable.reloadData()
    }
    
    private var presenter: FrameworksPresenter! {
        didSet { setPresenter(presenter) }
    }
    
    override func viewDidLoad() {
        presenter = FrameworksPresenter()
        super.viewDidLoad()
        frameworksTable.delegate = self
        frameworksTable.dataSource = self
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.tabBarController?.viewControllers?.remove(at: 0)
    }
}

extension FrameworksController: UITableViewDelegate {
    
}

extension FrameworksController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: FrameworkCell = tableView.cellFor(indexPath)
        let item = items[indexPath.row]
        cell.setup(item, presenter)
        return cell
    }
}

class FrameworkCell: UITableViewCell {
    
    @IBOutlet weak var titleLabel: UILabel!
    
    @IBAction func increaseTapped(_ sender: Any) {
        presenter.onFrameworkClick(framework: item.framework)
        presenter.onIncreaseClick(framework: item.framework)
    }
    
    @IBAction func decreaseTapped(_ sender: Any) {
        presenter.onDecreaseClick(framework: item.framework)
    }
    
    private var item: Basket.Item!
    private var presenter: FrameworksPresenter!
    func setup(_ item: Basket.Item, _ presenter: FrameworksPresenter) {
        self.item = item
        self.presenter = presenter
        titleLabel.text = "\(item.framework.name) (\(item.quantity))"
    }
}
