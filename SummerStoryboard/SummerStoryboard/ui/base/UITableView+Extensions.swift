//
//  TableView+Extensions.swift
//  iosApp
//
//  Created by adev on 14.03.2020.
//

import UIKit

extension UITableView {
    
    func cellFor<Cell: UITableViewCell>(
        _ indexPath: IndexPath
    ) -> Cell {
        return self.dequeueReusableCell(
            withIdentifier: Cell.reuseIdentifier,
            for: indexPath
        ) as! Cell
    }
}

extension UITableViewCell {
    
    static var reuseIdentifier: String {
        return String(describing: self)
    }
}
